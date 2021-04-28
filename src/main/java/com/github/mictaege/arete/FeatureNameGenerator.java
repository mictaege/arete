package com.github.mictaege.arete;

import static com.github.mictaege.arete.NamingTools.Capitalizer.CAPITALIZE_ALL;
import static com.github.mictaege.arete.NamingTools.toWords;
import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

import java.lang.reflect.Method;
import java.util.Optional;

import org.junit.jupiter.api.DisplayNameGenerator;

import com.google.common.base.Strings;

public class FeatureNameGenerator implements DisplayNameGenerator {

    @Override
    public String generateDisplayNameForClass(final Class<?> testClass) {
        return testClass.getSimpleName();
    }

    @Override
    public String generateDisplayNameForNestedClass(final Class<?> nestedClass) {
        return desc(nestedClass)
                .orElse("Feature: " + toWords(nestedClass.getSimpleName(), CAPITALIZE_ALL));
    }

    @Override
    public String generateDisplayNameForMethod(final Class<?> testClass, final Method testMethod) {
        return testMethod.getName();
    }

    private Optional<String> desc(final Class<?> nestedClass) {
        return findAnnotation(nestedClass, Feature.class)
                .map(Feature::desc)
                .map(Strings::emptyToNull);
    }
    
}
