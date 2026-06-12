package com.github.mictaege.arete;

import org.junit.jupiter.api.extension.*;

import java.lang.reflect.InvocationTargetException;
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

        final List<String> variants = methodsOf(journeyClass)
                .map(method -> findAnnotation(method, Step.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .flatMap(s -> Arrays.stream(s.variant()))
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

    private static Stream<Method> methodsOf(final Class<?> type) {
        if (type == null || type == Object.class) {
            return Stream.empty();
        }
        return Stream.concat(
                Arrays.stream(type.getDeclaredMethods()),
                methodsOf(type.getSuperclass())
        );
    }

    private record JourneyVariantInvocationContext(String variant) implements ClassTemplateInvocationContext {
        @Override
        public String getDisplayName(final int invocationIndex) {
            if (variant.isEmpty()) {
                return "Default";
            }
            return "Variant: " + variant;
        }

        @Override
        public List<Extension> getAdditionalExtensions() {
            return List.of(
                    new BeforeVariantCallback(variant),
                    new JourneyVariantCondition(variant)
            );
        }
    }

    private record BeforeVariantCallback(String variant) implements BeforeEachCallback {
        @Override
        public void beforeEach(final ExtensionContext context) throws Exception {
            final ExtensionContext variantContext = context.getParent().orElse(context);
            final ExtensionContext.Store store = variantContext.getStore(
                    ExtensionContext.Namespace.create(VariableJourneyExtension.class, variant)
            );

            final String alreadyExecutedKey = "beforeVariantExecuted";

            if (store.get(alreadyExecutedKey, Boolean.class) != null) {
                return;
            }

            final Object testInstance = context.getRequiredTestInstance();
            final Class<?> testClass = context.getRequiredTestClass();

            final List<Method> beforeVariantMethods = methodsOf(testClass)
                    .filter(method -> findAnnotation(method, BeforeVariant.class)
                            .map(this::matchesVariant)
                            .orElse(false))
                    .toList();

            for (final Method method : beforeVariantMethods) {
                invoke(method, testInstance);
            }

            store.put(alreadyExecutedKey, true);
        }

        private boolean matchesVariant(final BeforeVariant beforeVariant) {
            final List<String> beforeVariants = Stream.of(beforeVariant.variant())
                    .map(String::trim)
                    .filter(v -> !v.isBlank())
                    .toList();
            return beforeVariants.isEmpty() || beforeVariants.contains(variant);
        }

        private static void invoke(final Method method, final Object testInstance) throws Exception {
            try {
                method.setAccessible(true);
                method.invoke(testInstance);
            } catch (final InvocationTargetException e) {
                final Throwable cause = e.getCause();
                if (cause instanceof Exception exception) {
                    throw exception;
                }
                if (cause instanceof Error error) {
                    throw error;
                }
                throw e;
            }
        }
    }

    private record JourneyVariantCondition(String selectedVariant) implements ExecutionCondition {
        @Override
        public ConditionEvaluationResult evaluateExecutionCondition(final ExtensionContext context) {
            final Optional<Method> testMethod = context.getTestMethod();

            if (testMethod.isEmpty()) {
                return ConditionEvaluationResult.enabled("Not a journey step");
            }

            if (selectedVariant.isEmpty()) {
                return ConditionEvaluationResult.enabled("Step has no variant");
            }

            final Optional<Step> step = findAnnotation(testMethod.get(), Step.class);

            if (step.isEmpty()) {
                return ConditionEvaluationResult.enabled("Not a journey step");
            }

            final List<String> stepVariants = Stream.of(step.get().variant()).map(String::trim).filter(v -> !v.isBlank()).toList();

            if (stepVariants.isEmpty()) {
                return ConditionEvaluationResult.enabled("Step has no variant");
            }

            if (stepVariants.contains(selectedVariant)) {
                return ConditionEvaluationResult.enabled("Step matches journey variant " + selectedVariant);
            }

            return ConditionEvaluationResult.disabled(
                    "Step variants " + stepVariants + " does not match journey variant " + selectedVariant
            );
        }
    }

}