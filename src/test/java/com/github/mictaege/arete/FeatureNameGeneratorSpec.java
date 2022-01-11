package com.github.mictaege.arete;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.github.mictaege.arete.CalculatorGherkinStyleSpec.Addition;
import com.github.mictaege.arete.CalculatorGherkinStyleSpec.Addition.ShouldAddTwoNumbers;
import com.github.mictaege.arete.CalculatorGherkinStyleSpec.Subtracting;

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
            final String displayName = generator.generateDisplayNameForNestedClass(Subtracting.class);
            assertThat(displayName, is("Feature: Subtracting numbers"));
        }

        @ItShould void deriveFeatureDisplayFromClassName() {
            final String displayName = generator.generateDisplayNameForNestedClass(Addition.class);
            assertThat(displayName, is("Feature: Addition"));
        }
    }

    @Describe class DisplayNameGenerationForMethod {

        @ItShould void deriveDisplayFromMethodName() throws NoSuchMethodException {
            final Class<ShouldAddTwoNumbers> clazz = ShouldAddTwoNumbers.class;
            final String displayName = generator.generateDisplayNameForMethod(clazz, clazz.getDeclaredMethod("theResultShouldBeCorrect"));
            assertThat(displayName, is("the result should be correct"));
        }
    }

}