package com.github.mictaege.arete;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;

@Spec class CalculatorGherkinStyleSpec {

    @RegisterExtension
    public ScreenshotExtension screenshots = new ScreenshotExtension(new DummyScreenshotTaker());

    private Calculator calculator;

    @BeforeEach
    void context() {
        calculator = new Calculator();
    }

    @Feature class Addition {

        @Scenario class Should_Add_5_To_10 {
            int a, b, c;
            @Given(1) void five() {
                a = 5;
            }
            @Given(2) void ten() {
                b = a * 2;
            }

            @When void addingTogether() {
                c = calculator.add(a, b);
            }

            @Then(1) void theResultShouldBeCorrect() {
                assertThat(c, is(15));
            }

            @Then(2) void not_be_an_equal_number() {
                assertThat(c % 2, is(1));
            }
        }

        @Scenario class ShouldAddTwoNumbers {
            int d;
            @When(seq = 1, step = 1)
            void addingTwoNumbersTogether() {
                d = calculator.add(10, 5);
            }

            @Then(seq = 1, step = 1)
            void theResultShouldBeCorrect() {
                assertThat(d, is(15));
                assertThat(d, lessThan(16));
                assertThat(d % 2, is(1));
            }

            @When(seq = 2, step = 1)
            void addingTwoOtherNumbers() {
                d = calculator.add(10, 10);
            }

            @Then(seq = 2, step = 1)
            void theOtherResultShouldBeCorrect() {
                assertThat(d, is(20));
            }

            @Then(seq = 2, step = 2)
            void notBeToLarge() {
                assertThat(d, lessThan(21));
            }

            @Then(seq = 2, step = 3)
            void beAnEqualNumber() {
                assertThat(d % 2, is(0));
            }

            @Examples(pattern = "{0} + {1} => {2}", srcMethod = "shouldAddTwoNumbersExamples")
            void shouldAddTwoNumbers(final int a, final int b, final int expected) {
                assertThat(calculator.add(a, b), is(expected));
            }

            void shouldAddTwoNumbersExamples(final ExampleSource s) {
                s.example(s.given(3), s.given(4), s.then(7));
                s.example(s.given(-3), s.given(4), s.then(1));
                s.example(s.given(-3), s.given(-4), s.then(-7));
            }
        }

        @Scenario class ShouldAddThreeNumbers {
            int a, b, c, d;

            @Given(1) void aFirstNumber() {
                a = 5;
            }

            @Given(2) void aSecondNumber() {
                b = 7;
            }

            @Given(3) void aThirdNumber() {
                c = 3;
            }

            @When void addingTogether() {
                d = calculator.add(a, b, c);
            }

            @Then(1) void theResultShouldBeCorrect() {
                assertThat(d, is(15));
            }

            @Then(2) void notBeToLarge() {
                assertThat(d, lessThan(16));
            }

            @Then(3) void notBeAnEqualNumber() {
                assertThat(d % 2, is(1));
            }

            @Examples(pattern = "{0} + {1} + {2} => {3}", srcMethod = "shouldAddThreeNumbersExamples")
            void shouldAddThreeNumbers(final int a, final int b, final int c, final int expected) {
                assertThat(calculator.add(a, b, c), is(expected));
            }

            void shouldAddThreeNumbersExamples(final ExampleSource s) {
                s.example("{0}. Adding only positive numbers:",
                        s.given(3), s.given(4), s.given(5), s.then(12));
                s.example("{0}. Adding positive and negative numbers:",
                        s.given(-3), s.given(4), s.given(-5), s.then(-4));
                s.example("{0}. Adding only negative numbers:",
                        s.given(-3), s.given(-4), s.given(-5), s.then(-12));
            }

        }

    }

    @Feature(desc = "Feature: Subtracting numbers")
    class Subtracting {
        @Scenario(desc = "Scenario: Should subtract two numbers leading to a negative result")
        class ShouldSubtractTwoNumbers {
            int a;
            @Given(step = 1, desc = "Given the number 5") void aFirstNumber() {
                a = 5;
            }

            int b;
            @Given(step = 2, desc = "And the number 10") void aSecondNumber() {
                b = 10;
            }

            int c;
            @When void subtractingFromEachOther() {
                c = calculator.subtract(a, b);
            }

            @Then void theResultShouldBeCorrect() {
                assertThat(c, is(-5));
            }

            @Examples(pattern = "{0} - {1} => {2}", srcClass = SubtractTwoNumbersExamples.class)
            void subtractTwoNumbers(final int a, final int b, final int expected) {
                assertThat(calculator.subtract(a, b), is(expected));
            }

            class SubtractTwoNumbersExamples extends ExampleSource{
                @Override
                protected void init() {
                    example(given(3), given(4), then(-1));
                    example(given(-3), given(4), then(-7));
                    example(given(-3), given(-4), then(1));
                }

            }

        }
    }

}