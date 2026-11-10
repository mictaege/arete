package com.github.mictaege.arete;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor.Invocation;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

class StepContextExtensionTest {

    private final StepContextExtension extension = new StepContextExtension();

    @Test
    @SuppressWarnings("unchecked")
    void setsAndClearsCurrentAroundStepInvocation() throws Throwable {
        final Invocation<Void> invocation = mock(Invocation.class);
        final ReflectiveInvocationContext<Method> invocationContext = mock(ReflectiveInvocationContext.class);
        final ExtensionContext context = mock(ExtensionContext.class);
        doAnswer(inv -> {
            // holder is set while the step body runs
            StepContext.current();
            return null;
        }).when(invocation).proceed();

        extension.interceptTestMethod(invocation, invocationContext, context);

        // holder is cleared after the step
        assertThrows(IllegalStateException.class, StepContext::current);
    }

    @Test
    @SuppressWarnings("unchecked")
    void clearsCurrentEvenWhenStepFails() throws Throwable {
        final Invocation<Void> invocation = mock(Invocation.class);
        final ReflectiveInvocationContext<Method> invocationContext = mock(ReflectiveInvocationContext.class);
        final ExtensionContext context = mock(ExtensionContext.class);
        doAnswer(inv -> {
            throw new AssertionError("step failed");
        }).when(invocation).proceed();

        assertThrows(AssertionError.class,
                () -> extension.interceptTestMethod(invocation, invocationContext, context));
        assertThrows(IllegalStateException.class, StepContext::current);
    }

}
