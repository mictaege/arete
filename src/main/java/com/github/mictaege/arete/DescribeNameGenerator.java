package com.github.mictaege.arete;

import static com.github.mictaege.arete.NamingTools.Capitalizer.CAPITALIZE_ALL;
import static com.github.mictaege.arete.NamingTools.Capitalizer.UN_CAPITALIZE_ALL;
import static com.github.mictaege.arete.NamingTools.toWords;
import static java.util.Optional.ofNullable;
import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.DisplayNameGenerator;

import com.google.common.base.Strings;

public class DescribeNameGenerator implements DisplayNameGenerator {

    @Override
    public String generateDisplayNameForClass(final Class<?> testClass) {
        return testClass.getSimpleName();
    }

    @Override
    public String generateDisplayNameForNestedClass(final Class<?> nestedClass) {
        return desc(nestedClass)
                .orElse("Describe: " + toWords(nestedClass.getSimpleName(), CAPITALIZE_ALL));
    }

    @Override
    public String generateDisplayNameForMethod(final Class<?> testClass, final Method testMethod) {
        return desc(testMethod)
                .orElse(prefix(testMethod) + toWords(testMethod.getName(), UN_CAPITALIZE_ALL));
    }

    private Optional<String> desc(final Class<?> nestedClass) {
        return findAnnotation(nestedClass, Describe.class)
                .map(Describe::desc)
                .map(Strings::emptyToNull);
    }

    private Optional<String> desc(final Method testMethod) {
        final AtomicReference<String> desc = new AtomicReference<>("");
        findAnnotation(testMethod, ItShould.class).ifPresent(i -> {
            desc.set(i.desc());
        });
        findAnnotation(testMethod, Examples.class).ifPresent(e -> {
            desc.set(e.desc());
        });
        return ofNullable(desc.get()).map(Strings::emptyToNull);
    }

    private String prefix(final Method testMethod) {
        final AtomicReference<String> prefix = new AtomicReference<>("");
        findAnnotation(testMethod, ItShould.class).ifPresent(g -> {
            prefix.set("It should ");
        });
        findAnnotation(testMethod, Examples.class).ifPresent(g -> {
            prefix.set("Examples: ");
        });
        return prefix.get();
    }

}
