package com.github.mictaege.arete;

import com.google.common.base.Strings;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.mictaege.arete.NamingTools.Capitalizer.*;
import static com.github.mictaege.arete.NamingTools.toWords;
import static java.util.Optional.ofNullable;
import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

public class JourneyNameGenerator implements DisplayNameGenerator {

    @Override
    public String generateDisplayNameForClass(final Class<?> testClass) {
        return testClass.getSimpleName();
    }

    @Override
    public String generateDisplayNameForNestedClass(List<Class<?>> enclosingInstanceTypes, final Class<?> nestedClass) {
        return desc(nestedClass)
                .orElse("Journey: " + toWords(nestedClass.getSimpleName(), CAPITALIZE_ALL));
    }

    @Override
    public String generateDisplayNameForMethod(List<Class<?>> enclosingInstanceTypes, final Class<?> testClass, final Method testMethod) {
        return desc(testMethod).map(d -> prefix(testMethod) + d)
                .orElse(prefix(testMethod) + toWords(testMethod.getName(), CAPITALIZE_FIRST));
    }

    private Optional<String> desc(final Class<?> nestedClass) {
        return findAnnotation(nestedClass, Journey.class)
                .map(Journey::desc)
                .map(Strings::emptyToNull);
    }

    private Optional<String> desc(final Method testMethod) {
        final AtomicReference<String> desc = new AtomicReference<>("");
        findAnnotation(testMethod, Step.class).ifPresent(i -> {
            desc.set(i.desc());
        });
        return ofNullable(desc.get()).map(Strings::emptyToNull);
    }

    private String prefix(final Method testMethod) {
        final AtomicReference<String> phase = new AtomicReference<>("");
        findAnnotation(testMethod, Step.class).ifPresent(i -> {
            phase.set(i.phase());
        });
        return ofNullable(phase.get()).map(Strings::emptyToNull).map(p -> p + " ").orElse("") + "⏵️ ";
    }

}
