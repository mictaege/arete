package com.github.mictaege.arete;

import static com.google.common.collect.Lists.reverse;
import static java.util.Optional.ofNullable;
import static org.junit.platform.commons.util.ReflectionUtils.isInnerClass;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.junit.platform.commons.util.ReflectionUtils;

final class InnerClassUtils {

    static <T> T newInstance(final Class<T> innerClass) {
        try {
            final List<Class<?>> path = buildPathToInner(innerClass);
            Object outer = null;
            Object inner = null;
            for (Class<?> aClass : path) {
                if (!isInnerClass(aClass)) {
                    outer = ReflectionUtils.newInstance(aClass);
                    inner = ReflectionUtils.newInstance(aClass);
                } else {
                    final Class<?> outerCls = ofNullable(outer).map(Object::getClass).orElseThrow(() -> {
                        return new IllegalStateException("Missing outer class");
                    });
                    final Constructor<?> constructor = findInnerClassConstructor(aClass, outerCls);
                    inner = ReflectionUtils.newInstance(constructor, outer);
                    outer = ReflectionUtils.newInstance(constructor, outer);
                }
            }
            return (T)inner;
        } catch (final Exception e) {
            throw new IllegalStateException("Failed to create new instance of inner class " + innerClass.getSimpleName(), e);
        }
    }

    private static List<Class<?>> buildPathToInner(final Class<?> nestedCls) {
        final List<Class<?>> buffer = new ArrayList<>();
        walkToOuter(buffer, nestedCls);
        return reverse(buffer);
    }

    private static void walkToOuter(final List<Class<?>> buffer, final Class<?> nestedCls) {
        buffer.add(nestedCls);
        if (isInnerClass(nestedCls)) {
            walkToOuter(buffer, nestedCls.getEnclosingClass());
        }
    }

    private static Constructor<?> findInnerClassConstructor(final Class<?> aClass, final Class<?> outerCls) {
        try {
            final Constructor<?> constructor = aClass.getDeclaredConstructor(outerCls);
            constructor.setAccessible(true);
            return constructor;
        } catch (final Exception e) {
            throw new IllegalStateException("Unable to find inner class constructor of class " + aClass.getSimpleName(), e);
        }
    }

}
