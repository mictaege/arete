package com.github.mictaege.arete;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

/**
 * Makes the current narrative available via {@link NarrativeContext#current()} by holding its
 * class-level {@link ExtensionContext} in a {@link ThreadLocal} for the duration of the
 * {@code @BeforeAll}, {@code @AfterAll} and test-class constructor invocations.
 */
public class NarrativeContextExtension implements InvocationInterceptor {

    @Override
    public <T> T interceptTestClassConstructor(
            final Invocation<T> invocation,
            final ReflectiveInvocationContext<Constructor<T>> invocationContext,
            final ExtensionContext extensionContext) throws Throwable {
        NarrativeContext.setCurrent(extensionContext);
        try {
            return invocation.proceed();
        } finally {
            NarrativeContext.clearCurrent();
        }
    }

    @Override
    public void interceptBeforeAllMethod(
            final Invocation<Void> invocation,
            final ReflectiveInvocationContext<Method> invocationContext,
            final ExtensionContext extensionContext) throws Throwable {
        NarrativeContext.setCurrent(extensionContext);
        try {
            invocation.proceed();
        } finally {
            NarrativeContext.clearCurrent();
        }
    }

    @Override
    public void interceptAfterAllMethod(
            final Invocation<Void> invocation,
            final ReflectiveInvocationContext<Method> invocationContext,
            final ExtensionContext extensionContext) throws Throwable {
        NarrativeContext.setCurrent(extensionContext);
        try {
            invocation.proceed();
        } finally {
            NarrativeContext.clearCurrent();
        }
    }

}
