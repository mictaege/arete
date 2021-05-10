package com.github.mictaege.arete;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

import org.junit.jupiter.api.BeforeEach;

@Spec class CalculatorGherkinStyleSpec {

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
        }
    }

}