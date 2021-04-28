package com.github.mictaege.arete;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;

class CalculatorDescriptiveStyleSpec {

    @Describe() class ACalculator {
        private Calculator calculator;

        @BeforeEach
        void context() {
            calculator = new Calculator();
        }

        @Describe() class Subtraction {
            @ItShould void subtract5From10() {
                assertThat(calculator.subtract(10, 5), is(5));
            }

            @ItShould void subtractTwoNumbers() {
                assertThat(calculator.subtract(5, 10), is(-5));
            }
        }

        @Describe() class Addition {
            @ItShould(desc = "It should add 5 to 10") void addTwoNumbers() {
                assertThat(calculator.add(5, 10), is(15));
            }

            @ItShould void addThreeNumbers() {
                assertThat(calculator.add(5, 7, 3), is(15));
            }
        }

        @Describe() class Division {
            @ItShould void divideTwoNumbers() {
                assertThat(calculator.divide(6, 3), is(2.0));
            }

            @ItShould void notDivideByZero() {
                assertThrows(IllegalArgumentException.class, () -> {
                    calculator.divide(6, 0);
                });
            }
        }
    }

}