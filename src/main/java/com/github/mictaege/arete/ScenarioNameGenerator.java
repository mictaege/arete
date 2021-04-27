package com.github.mictaege.arete;

import static com.github.mictaege.arete.NamingTools.Capitalizer.CAPITALIZE_ALL;
import static com.github.mictaege.arete.NamingTools.Capitalizer.UN_CAPITALIZE_ALL;
import static com.github.mictaege.arete.NamingTools.toWords;
import static java.lang.Math.max;
import static java.util.Optional.ofNullable;
import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.DisplayNameGenerator;

import com.google.common.base.Strings;

public class ScenarioNameGenerator implements DisplayNameGenerator {

    @Override
    public String generateDisplayNameForClass(final Class<?> testClass) {
        return testClass.getSimpleName();
    }

    @Override
    public String generateDisplayNameForNestedClass(final Class<?> nestedClass) {
        return desc(nestedClass)
                .orElse("Scenario: " + toWords(nestedClass.getSimpleName(), CAPITALIZE_ALL));
    }

    @Override
    public String generateDisplayNameForMethod(final Class<?> testClass, final Method testMethod) {
        return desc(testMethod)
                .orElse(prefix(testMethod) + toWords(testMethod.getName(), UN_CAPITALIZE_ALL));
    }

    private Optional<String> desc(final Class<?> nestedClass) {
        return findAnnotation(nestedClass, Scenario.class)
                .map(Scenario::desc)
                .map(Strings::emptyToNull);
    }

    private Optional<String> desc(final Method testMethod) {
        final AtomicReference<String> desc = new AtomicReference<>("");
        findAnnotation(testMethod, Given.class).ifPresent(g -> {
            desc.set(g.desc());
        });
        findAnnotation(testMethod, When.class).ifPresent(w -> {
            desc.set(w.desc());
        });
        findAnnotation(testMethod, Then.class).ifPresent(t -> {
            desc.set(t.desc());
        });
        return ofNullable(desc.get()).map(Strings::emptyToNull);
    }

    private String prefix(final Method testMethod) {
        final AtomicReference<String> prefix = new AtomicReference<>("");
        findAnnotation(testMethod, Given.class).ifPresent(g -> {
            if (max(g.value(), g.step()) == 1) {
                prefix.set("Given ");
            } else {
                prefix.set("And ");
            }
        });
        findAnnotation(testMethod, When.class).ifPresent(w -> {
            if (max(w.value(), w.step()) == 1) {
                prefix.set("When ");
            } else {
                prefix.set("And ");
            }
        });
        findAnnotation(testMethod, Then.class).ifPresent(t -> {
            if (max(t.value(), t.step()) == 1) {
                prefix.set("Then ");
            } else {
                prefix.set("And ");
            }
        });
        return prefix.get();
    }

}
