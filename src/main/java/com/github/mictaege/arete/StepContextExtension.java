package com.github.mictaege.arete;

import java.lang.reflect.Method;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

/**
 * Makes the currently executing step available via {@link StepContext#current()} by holding its
 * {@link ExtensionContext} in a {@link ThreadLocal} for the duration of the step invocation.
 */
public class StepContextExtension implements InvocationInterceptor {

    @Override
    public void interceptTestMethod(
            final Invocation<Void> invocation,
            final ReflectiveInvocationContext<Method> invocationContext,
            final ExtensionContext extensionContext) throws Throwable {
        StepContext.setCurrent(extensionContext);
        try {
            invocation.proceed();
        } finally {
            StepContext.clearCurrent();
        }
    }

}
