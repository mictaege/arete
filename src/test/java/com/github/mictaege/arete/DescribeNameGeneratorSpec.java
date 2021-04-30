package com.github.mictaege.arete;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.github.mictaege.arete.CalculatorDescriptiveStyleSpec.ACalculator;
import com.github.mictaege.arete.CalculatorDescriptiveStyleSpec.ACalculator.Addition;
import com.github.mictaege.arete.CalculatorDescriptiveStyleSpec.ACalculator.Subtraction;

class DescribeNameGeneratorSpec {

    DescribeNameGenerator generator = new DescribeNameGenerator();

    @Describe class DisplayNameGenerationForClass {

        @ItShould void takeSimpleClassName() {
            final String displayName = generator.generateDisplayNameForClass(CalculatorDescriptiveStyleSpec.class);
            assertThat(displayName, is("CalculatorDescriptiveStyleSpec"));
        }
    }

    @Describe class DisplayNameGenerationForNestedClass {

        @ItShould void takeExplicitDescriptionIfPresent() {
            final String displayName = generator.generateDisplayNameForNestedClass(ACalculator.class);
            assertThat(displayName, is("A Calculator Instance"));
        }

        @ItShould void deriveDescriptiveDisplayFromClassName() {
            final String displayName = generator.generateDisplayNameForNestedClass(Subtraction.class);
            assertThat(displayName, is("Describe: Subtraction"));
        }
    }

    @Describe class DisplayNameGenerationForMethod {

        @ItShould void takeExplicitDescriptionIfPresent() throws NoSuchMethodException {
            final Class<Addition> clazz = Addition.class;
            final String displayName = generator.generateDisplayNameForMethod(clazz, clazz.getDeclaredMethod("addTwoNumbers"));
            assertThat(displayName, is("It should add 5 to 10"));
        }

        @ItShould void deriveItShouldDisplayFromMethodName() throws NoSuchMethodException {
            final Class<Addition> clazz = Addition.class;
            final String displayName = generator.generateDisplayNameForMethod(clazz, clazz.getDeclaredMethod("addThreeNumbers"));
            assertThat(displayName, is("It should add three numbers"));
        }
    }

}