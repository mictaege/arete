package com.github.mictaege.arete;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.google.common.base.Joiner;

@Spec class CalculatorDescriptiveStyleSpec {

    @RegisterExtension
    public ScreenshotExtension screenshots = new ScreenshotExtension(new DummyScreenshotTaker());

    @Describe(desc = "A Calculator Instance") class ACalculator {
        private Calculator calculator;

        @BeforeEach
        void context() {
            calculator = new Calculator();
        }

        @Describe class Subtraction {

            @ItShould void subtractTwoNumbers() {
                subtractTwoNumbers(5, 10, -5);
            }

            @Examples(pattern = "{0} - {1} => {2}", srcMethod = "subtractTwoNumbers")
            void subtractTwoNumbers(final int a, final int b, final int expected) {
                assertThat(calculator.subtract(a, b), is(expected));
            }

            void subtractTwoNumbers(final ExampleSource s) {
                s.example(s.given(10),  s.given(3), s.then(7));
                s.example(s.given(10),  s.given(10), s.then(0));
                s.example(s.given(10),  s.given(17), s.then(-7));
                s.example(s.given(-5),  s.given(5), s.then(-10));
                s.example(s.given(-5),  s.given(-5), s.then(0));
                s.example(s.given(-5),  s.given(-7), s.then(2));
            }

        }

        @Describe class Addition {

            @ItShould(desc = "It should add 5 to 10") void addTwoNumbers() {
                addSomeNumbers(15, 5, 10);
            }

            @ItShould void addThreeNumbers() {
                addSomeNumbers(15, 5, 7, 3);
            }

            @ItShould void addFourNumbers() {
                addSomeNumbers(13, 5, 7, 3, -2);
            }

            @Examples(pattern = "{1} => {0}", srcMethod = "addSomeNumbers")
            void addSomeNumbers(final int exp, final Integer... numbers) {
                assertThat(calculator.add(numbers), is(exp));
            }

            void addSomeNumbers(final ExampleSource s) {
                final Function<Integer[], String> toStr = (n) -> Joiner.on(" + ").join(n);
                s.example("{0}. Adding only positive numbers:",
                        s.expect(34),  s.when(new Integer[]{3, 7, 4, 9, 11}, toStr));
                s.example("{0}. Adding positive and negative numbers:",
                        s.expect(-2),  s.when(new Integer[]{3, 7, -8, 2, -6}, toStr));
                s.example("{0}. Adding only negative numbers:",
                        s.expect(-26),  s.when(new Integer[]{-3, -7, -8, -2, -6}, toStr));
            }

        }

        @Describe class Division {
            @ItShould void divideTwoNumbers() {
                divideTwoNumbers(6, 3, 2.0);
            }

            @ItShould void notDivideByZero() {
                assertThrows(IllegalArgumentException.class, () -> {
                    calculator.divide(6, 0);
                });
            }

            @Examples(pattern = "{0} : {1} => {2}", srcClass = DivideTwoNumbers.class)
            void divideTwoNumbers(final int a, final int b, final double expected) {
                assertThat(calculator.divide(a, b), is(expected));
            }

            class DivideTwoNumbers extends ExampleSource {
                @Override
                protected void init() {
                    example(given(9), given(3), then(3.0));
                    example(given(9), given(-3), then(-3.0));
                    example(given(-9), given(3), then(-3.0));
                    example(given(-9), given(-3), then(3.0));
                }
            }

        }
    }

}