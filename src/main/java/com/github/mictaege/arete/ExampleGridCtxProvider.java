package com.github.mictaege.arete;

import static com.github.mictaege.arete.InnerClassUtils.newInstance;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;
import static org.junit.platform.commons.util.ReflectionUtils.findMethod;
import static org.junit.platform.commons.util.ReflectionUtils.invokeMethod;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import com.google.common.base.Strings;

public final class ExampleGridCtxProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(final ExtensionContext ctx) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(final ExtensionContext ctx) {
        final ExampleGrid gridDef = getAnnotation(ctx);
        final ExampleGridSource source;
        if (!Strings.isNullOrEmpty(gridDef.srcMethod())) {
            try {
                final Class<?> testCls = ctx.getRequiredTestClass();
                final Object enclosingObj = newInstance(testCls);
                final Method exampleMethod = findMethod(testCls, gridDef.srcMethod(), ExampleGridSource.class)
                        .orElseThrow(() -> {
                            return new NoSuchMethodException("Method " + gridDef.srcMethod() + "(ExampleGridSource) not present");
                        });
                source = new MethodExampleGridSource();
                invokeMethod(exampleMethod, enclosingObj, source);
            } catch (final Exception e) {
                throw new IllegalStateException("Failed to load data grid", e);
            }
        } else if (!gridDef.srcClass().equals(ExampleGridSource.class)) {
            try {
                source = newInstance(gridDef.srcClass());
            } catch (final Exception e) {
                throw new IllegalStateException("Failed to load data grid", e);
            }
        } else {
            throw new IllegalStateException("No valid @ExampleGrid#srcMethod or @ExampleGrid#srcClass declared");
        }
        return source.getRows().stream().map(p -> invocationContext(ctx, p));
    }

    private ExampleGrid getAnnotation(final ExtensionContext ctx) {
        return ctx.getTestMethod()
                .map(m -> MethodUtils.getAnnotation(m, ExampleGrid.class, false, true))
                .orElseThrow(() -> {
                    return new IllegalStateException("@ExampleGrid annotation missing");
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
