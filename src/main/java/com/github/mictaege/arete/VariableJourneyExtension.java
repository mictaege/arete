package com.github.mictaege.arete;

import org.junit.jupiter.api.extension.ClassTemplateInvocationContext;
import org.junit.jupiter.api.extension.ClassTemplateInvocationContextProvider;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.platform.commons.support.AnnotationSupport.findAnnotation;

public class VariableJourneyExtension implements ClassTemplateInvocationContextProvider {

    private static final String NO_VARIANT = "";

    @Override
    public boolean supportsClassTemplate(final ExtensionContext context) {
        return context.getTestClass()
                .flatMap(testClass -> findAnnotation(testClass, VariableJourney.class))
                .isPresent();
    }

    @Override
    public Stream<ClassTemplateInvocationContext> provideClassTemplateInvocationContexts(final ExtensionContext context) {
        final Class<?> journeyClass = context.getRequiredTestClass();

        final List<String> variants = Arrays.stream(journeyClass.getDeclaredMethods())
                .map(method -> findAnnotation(method, Step.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Step::variant)
                .map(String::trim)
                .filter(variant -> !variant.isEmpty())
                .distinct()
                .sorted()
                .toList();

        if (variants.isEmpty()) {
            return Stream.of(new JourneyVariantInvocationContext(NO_VARIANT));
        }

        return variants.stream().map(JourneyVariantInvocationContext::new);
    }

    private static class JourneyVariantInvocationContext implements ClassTemplateInvocationContext {

        private final String variant;

        private JourneyVariantInvocationContext(final String variant) {
            this.variant = variant;
        }

        @Override
        public String getDisplayName(final int invocationIndex) {
            if (variant.isEmpty()) {
                return "Default";
            }
            return "Variant: " + variant ;
        }

        @Override
        public List<Extension> getAdditionalExtensions() {
            return List.of(new JourneyVariantCondition(variant));
        }

    }

    private static class JourneyVariantCondition implements ExecutionCondition {

        private final String selectedVariant;

        private JourneyVariantCondition(final String selectedVariant) {
            this.selectedVariant = selectedVariant;
        }

        @Override
        public ConditionEvaluationResult evaluateExecutionCondition(final ExtensionContext context) {
            final Optional<Method> testMethod = context.getTestMethod();

            if (testMethod.isEmpty()) {
                return ConditionEvaluationResult.enabled("Not a journey step");
            }

            final Optional<Step> step = findAnnotation(testMethod.get(), Step.class);

            if (step.isEmpty()) {
                return ConditionEvaluationResult.enabled("Not a journey step");
            }

            final String stepVariant = step.get().variant().trim();

            if (stepVariant.isEmpty()) {
                return ConditionEvaluationResult.enabled("Step has no variant");
            }

            if (selectedVariant.isEmpty()) {
                return ConditionEvaluationResult.enabled("Journey has no variants");
            }

            if (stepVariant.equals(selectedVariant)) {
                return ConditionEvaluationResult.enabled("Step matches journey variant " + selectedVariant);
            }

            return ConditionEvaluationResult.disabled(
                    "Step variant " + stepVariant + " does not match journey variant " + selectedVariant
            );
        }

    }

}