package com.github.mictaege.arete;

import static com.github.mictaege.arete.InnerClassUtils.newInstance;
import static java.util.stream.Collectors.joining;
import static org.junit.platform.commons.util.ReflectionUtils.findMethod;
import static org.junit.platform.commons.util.ReflectionUtils.invokeMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import com.google.common.base.Strings;

public final class ExampleCsvCtxProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(final ExtensionContext ctx) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(final ExtensionContext ctx) {
        final ExampleCsv gridDef = getAnnotation(ctx);
        final ExampleCsvSource source;
        if (!Strings.isNullOrEmpty(gridDef.csvData())) {
            source = new AnnotationExampleCsvSource(gridDef.delimiter(), gridDef.csvData());
        } else if (!Strings.isNullOrEmpty(gridDef.csvResourcePath())) {
            source = new ResourceExampleCsvSource(gridDef.delimiter(), gridDef.csvResourcePath());
        } else if (!Strings.isNullOrEmpty(gridDef.srcMethod())) {
            try {
                final Class<?> testCls = ctx.getRequiredTestClass();
                final Object enclosingObj = newInstance(testCls);
                final Method exampleMethod = findMethod(testCls, gridDef.srcMethod(), ExampleCsvSource.class)
                        .orElseThrow(() -> {
                            return new NoSuchMethodException("Method " + gridDef.srcMethod() + "(ExampleCsvSource) not present");
                        });
                source = new MethodExampleCsvSource(gridDef.delimiter());
                invokeMethod(exampleMethod, enclosingObj, source);
            } catch (final Exception e) {
                throw new IllegalStateException("Failed to load csv data", e);
            }
        } else if (!gridDef.srcClass().equals(ExampleCsvSource.class)) {
            try {
                source = newInstance(gridDef.srcClass());
            } catch (final Exception e) {
                throw new IllegalStateException("Failed to load csv data", e);
            }
        } else {
            throw new IllegalStateException("Invalid @ExampleCsv declaration");
        }
        return source.getRows().stream().map(p -> invocationContext(ctx, p));
    }

    private ExampleCsv getAnnotation(final ExtensionContext ctx) {
        return ctx.getTestMethod()
                .map(m -> MethodUtils.getAnnotation(m, ExampleCsv.class, false, true))
                .orElseThrow(() -> {
                    return new IllegalStateException("@ExampleCsv annotation missing");
                });
    }

    private TestTemplateInvocationContext invocationContext(final ExtensionContext ctx,
                                                            final ExampleGridRow row) {
        return new TestTemplateInvocationContext() {

            @Override
            public String getDisplayName(final int idx) {
                return "| " + Arrays.stream(row.getData()).map(Object::toString).collect(joining(" | ")) + " |";
            }

            @Override
            public List<Extension> getAdditionalExtensions() {
                return Collections.singletonList(new ParameterResolver() {
                    @Override
                    public boolean supportsParameter(final ParameterContext paramCtx,
                                                     final ExtensionContext extCtx) {
                        return true;
                    }

                    @Override
                    public Object resolveParameter(final ParameterContext paramCtx,
                                                   final ExtensionContext extCtx) {
                        return row.getData()[paramCtx.getIndex()].getValue();
                    }
                });
            }
        };
    }
}
