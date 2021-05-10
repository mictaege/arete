package com.github.mictaege.arete;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.github.mictaege.arete.CalculatorGherkinStyleSpec.Addition.ShouldAddTwoNumbers;
import com.github.mictaege.arete.CalculatorGherkinStyleSpec.Addition.Should_Add_5_To_10;
import com.github.mictaege.arete.CalculatorGherkinStyleSpec.Subtracting.ShouldSubtractTwoNumbers;

@Spec class ScenarioNameGeneratorSpec {

    ScenarioNameGenerator generator = new ScenarioNameGenerator();

    @Describe class DisplayNameGenerationForClass {

        @ItShould void takeSimpleClassName() {
            final String displayName = generator.generateDisplayNameForClass(CalculatorGherkinStyleSpec.class);
            assertThat(displayName, is("CalculatorGherkinStyleSpec"));
        }
    }

    @Describe class DisplayNameGenerationForNestedClass {

        @ItShould void takeExplicitDescriptionIfPresent() {
            final String displayName = generator.generateDisplayNameForNestedClass(ShouldAddTwoNumbers.class);
            assertThat(displayName, is("Scenario: Should Add Two Numbers"));
        }

        @ItShould void deriveScenarioDisplayFromClassName() {
            final String displayName = generator.generateDisplayNameForNestedClass(ShouldSubtractTwoNumbers.class);
            assertThat(displayName, is("Scenario: Should subtract two numbers leading to a negative result"));
        }
    }

    @Describe class DisplayNameGenerationForMethod {

        @ItShould void takeExplicitDescriptionIfPresent() throws NoSuchMethodException {
            final Class<ShouldSubtractTwoNumbers> clazz = ShouldSubtractTwoNumbers.class;
            final String displayName = generator.generateDisplayNameForMethod(clazz, clazz.getDeclaredMethod("aFirstNumber"));
            assertThat(displayName, is("Given the number 5"));
        }

        @ItShould void deriveGivenDisplayFromMethodName() throws NoSuchMethodException {
            final Class<Should_Add_5_To_10> clazz = Should_Add_5_To_10.class;
            final String displayName = generator.generateDisplayNameForMethod(clazz, clazz.getDeclaredMethod("five"));
            assertThat(displayName, is("Given five"));
        }

        @ItShould void deriveAndGivenDisplayFromMethodName() throws NoSuchMethodException {
            final Class<Should_Add_5_To_10> clazz = Should_Add_5_To_10.class;
            final String displayName = generator.generateDisplayNameForMethod(clazz, clazz.getDeclaredMethod("ten"));
            assertThat(displayName, is("And ten"));
        }

        @ItShould void deriveWhenDisplayFromMethodName() throws NoSuchMethodException {
            final Class<Should_Add_5_To_10> clazz = Should_Add_5_To_10.class;
            final String displayName = generator.generateDisplayNameForMethod(clazz, clazz.getDeclaredMethod("addingTogether"));
            assertThat(displayName, is("When adding together"));
        }

        @ItShould void deriveThenDisplayFromMethodName() throws NoSuchMethodException {
            final Class<Should_Add_5_To_10> clazz = Should_Add_5_To_10.class;
            final String displayName = generator.generateDisplayNameForMethod(clazz, clazz.getDeclaredMethod("theResultShouldBeCorrect"));
            assertThat(displayName, is("Then the result should be correct"));
        }

        @ItShould void deriveAndThenDisplayFromMethodName() throws NoSuchMethodException {
            final Class<Should_Add_5_To_10> clazz = Should_Add_5_To_10.class;
            final String displayName = generator.generateDisplayNameForMethod(clazz, clazz.getDeclaredMethod("not_be_an_equal_number"));
            assertThat(displayName, is("And not be an equal number"));
        }
    }

}