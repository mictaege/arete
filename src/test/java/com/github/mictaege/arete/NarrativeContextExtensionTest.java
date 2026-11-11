package com.github.mictaege.arete;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor.Invocation;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

class NarrativeContextExtensionTest {

    private final NarrativeContextExtension extension = new NarrativeContextExtension();

    @Test
    @SuppressWarnings("unchecked")
    void setsAndClearsCurrentAroundConstructor() throws Throwable {
        final Invocation<Object> invocation = mock(Invocation.class);
        final ReflectiveInvocationContext<Constructor<Object>> invocationContext = mock(ReflectiveInvocationContext.class);
        final ExtensionContext context = mock(ExtensionContext.class);
        doAnswer(inv -> {
            assertDoesNotThrow(NarrativeContext::current);
            return null;
        }).when(invocation).proceed();

        extension.interceptTestClassConstructor(invocation, invocationContext, context);

        assertThrows(IllegalStateException.class, NarrativeContext::current);
    }

    @Test
    @SuppressWarnings("unchecked")
    void setsAndClearsCurrentAroundBeforeAll() throws Throwable {
        final Invocation<Void> invocation = mock(Invocation.class);
        final ReflectiveInvocationContext<Method> invocationContext = mock(ReflectiveInvocationContext.class);
        final ExtensionContext context = mock(ExtensionContext.class);
        doAnswer(inv -> {
            assertDoesNotThrow(NarrativeContext::current);
            return null;
        }).when(invocation).proceed();

        extension.interceptBeforeAllMethod(invocation, invocationContext, context);

        assertThrows(IllegalStateException.class, NarrativeContext::current);
    }

    @Test
    @SuppressWarnings("unchecked")
    void setsAndClearsCurrentAroundAfterAll() throws Throwable {
        final Invocation<Void> invocation = mock(Invocation.class);
        final ReflectiveInvocationContext<Method> invocationContext = mock(ReflectiveInvocationContext.class);
        final ExtensionContext context = mock(ExtensionContext.class);
        doAnswer(inv -> {
            assertDoesNotThrow(NarrativeContext::current);
            return null;
        }).when(invocation).proceed();

        extension.interceptAfterAllMethod(invocation, invocationContext, context);

        assertThrows(IllegalStateException.class, NarrativeContext::current);
    }

    @Test
    @SuppressWarnings("unchecked")
    void clearsCurrentEvenWhenBeforeAllFails() throws Throwable {
        final Invocation<Void> invocation = mock(Invocation.class);
        final ReflectiveInvocationContext<Method> invocationContext = mock(ReflectiveInvocationContext.class);
        final ExtensionContext context = mock(ExtensionContext.class);
        doAnswer(inv -> {
            throw new AssertionError("beforeAll failed");
        }).when(invocation).proceed();

        assertThrows(AssertionError.class,
                () -> extension.interceptBeforeAllMethod(invocation, invocationContext, context));
        assertThrows(IllegalStateException.class, NarrativeContext::current);
    }

}
