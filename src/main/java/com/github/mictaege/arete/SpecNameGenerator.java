package com.github.mictaege.arete;

import static com.github.mictaege.arete.NamingTools.Capitalizer.CAPITALIZE_ALL;
import static com.github.mictaege.arete.NamingTools.Capitalizer.UN_CAPITALIZE_ALL;
import static com.github.mictaege.arete.NamingTools.toWords;
import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

import java.lang.reflect.Method;
import java.util.Optional;

import org.junit.jupiter.api.DisplayNameGenerator;

import com.google.common.base.Strings;

public class SpecNameGenerator implements DisplayNameGenerator {

    @Override
    public String generateDisplayNameForClass(final Class<?> testClass) {
        return desc(testClass)
                .orElse(toWords(testClass.getSimpleName(), CAPITALIZE_ALL));
    }

    @Override
    public String generateDisplayNameForNestedClass(final Class<?> nestedClass) {
        return desc(nestedClass)
                .orElse(toWords(nestedClass.getSimpleName(), CAPITALIZE_ALL));
    }

    @Override
    public String generateDisplayNameForMethod(final Class<?> testClass, final Method testMethod) {
        return toWords(testMethod.getName(), UN_CAPITALIZE_ALL);
    }

    private Optional<String> desc(final Class<?> nestedClass) {
        return findAnnotation(nestedClass, Spec.class)
                .map(Spec::desc)
                .map(Strings::emptyToNull);
    }

}
