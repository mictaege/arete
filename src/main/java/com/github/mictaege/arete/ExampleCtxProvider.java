package com.github.mictaege.arete;

import static com.github.mictaege.arete.InnerClassUtils.newInstance;
import static org.junit.platform.commons.util.ReflectionUtils.findMethod;
import static org.junit.platform.commons.util.ReflectionUtils.invokeMethod;

import java.lang.reflect.Method;
import java.text.MessageFormat;
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

public final class ExampleCtxProvider implements TestTemplateInvocationContextProvider {

    @Override
    public boolean supportsTestTemplate(final ExtensionContext ctx) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(final ExtensionContext ctx) {
        final Examples examDef = getAnnotation(ctx);
        final ExampleSource source;
        if (!Strings.isNullOrEmpty(examDef.srcMethod())) {
            try {
                final Class<?> testCls = ctx.getRequiredTestClass();
                final Object enclosingObj = newInstance(testCls);
                final Method exampleMethod = findMethod(testCls, examDef.srcMethod(), ExampleSource.class)
                        .orElseThrow(() -> {
                            return new NoSuchMethodException("Method " + examDef.srcMethod() + "(ExampleSource) not present");
                        });
                source = new MethodExampleSource();
                invokeMethod(exampleMethod, enclosingObj, source);
            } catch (final Exception e) {
                throw new IllegalStateException("Failed to load example parameters", e);
            }
        } else if (!examDef.srcClass().equals(ExampleSource.class)) {
            try {
                source = newInstance(examDef.srcClass());
            } catch (final Exception e) {
                throw new IllegalStateException("Failed to load example parameters", e);
            }
        } else {
            throw new IllegalStateException("No valid @Examples#srcMethod or @Examples#srcClass declared");
        }
        return source.getExamples().stream().map(p -> invocationContext(ctx, p));
    }

    private Examples getAnnotation(final ExtensionContext ctx) {
        return ctx.getTestMethod()
                .map(m -> MethodUtils.getAnnotation(m, Examples.class, false, true))
                .orElseThrow(() -> {
                    return new IllegalStateException("@Examples annotation missing");
                });
    }

    private TestTemplateInvocationContext invocationContext(final ExtensionContext ctx, final ExampleParams params) {
        return new TestTemplateInvocationContext() {

            @Override
            public String getDisplayName(final int idx) {
                final String pattern = getAnnotation(ctx).pattern();
                return params.getPrefix(idx) + " " + MessageFormat.format(pattern, (Object[]) params.getParams());
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
                        return params.getParams()[paramCtx.getIndex()].getValue();
                    }
                });
            }
        };
    }
}
