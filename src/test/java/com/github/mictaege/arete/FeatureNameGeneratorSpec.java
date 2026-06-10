package com.github.mictaege.arete;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.github.mictaege.arete.CalculatorGherkinStyleSpec.Addition;
import com.github.mictaege.arete.CalculatorGherkinStyleSpec.Addition.ShouldAddTwoNumbers;
import com.github.mictaege.arete.CalculatorGherkinStyleSpec.Subtracting;

import java.util.List;

@Spec class FeatureNameGeneratorSpec {

    FeatureNameGenerator generator = new FeatureNameGenerator();

    @Describe class DisplayNameGenerationForClass {

        @ItShould void takeSimpleClassName() {
            final String displayName = generator.generateDisplayNameForClass(CalculatorGherkinStyleSpec.class);
            assertThat(displayName, is("CalculatorGherkinStyleSpec"));
        }
    }

    @Describe class DisplayNameGenerationForNestedClass {

        @ItShould void takeExplicitDescriptionIfPresent() {
            final String displayName = generator.generateDisplayNameForNestedClass(List.of(), Subtracting.class);
            assertThat(displayName, is("Feature: Subtracting numbers"));
        }

        @ItShould void deriveFeatureDisplayFromClassName() {
            final String displayName = generator.generateDisplayNameForNestedClass(List.of(), Addition.class);
            assertThat(displayName, is("Feature: Addition"));
        }
    }

    @Describe class DisplayNameGenerationForMethod {

        @ItShould void deriveDisplayFromMethodName() throws NoSuchMethodException {
            final Class<ShouldAddTwoNumbers> clazz = ShouldAddTwoNumbers.class;
            final String displayName = generator.generateDisplayNameForMethod(List.of(), clazz, clazz.getDeclaredMethod("theResultShouldBeCorrect"));
            assertThat(displayName, is("the result should be correct"));
        }
    }

}