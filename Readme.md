# Arete

[![Apache License 2.0](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.mictaege/arete.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.mictaege%22%20AND%20a:%22arete%22)

Arete is a lightweight JUnit 5 extension for writing specifications and scenarios in a BDD testing style.

## Overview

### Gerkhin style

```Java
class CalculatorGherkinStyleSpec {

    private Calculator calculator;

    @BeforeEach
    void context() {
        calculator = new Calculator();
    }

    @Scenario
    class ShouldAddFiveToTen {
        int a, b, c;

        @Given void fiveAndTen() {
            a = 5;
            b = 10;
        }

        @When void addingTogether() {
            c = calculator.add(a, b);
        }

        @Then void theResultShouldBeCorrect() {
            assertThat(c, is(15));
        }
    }
}
```
A specification written in Gerkhin style contains one or more scenarios as nested classes. Each scenario defines several executable steps.

See [source](/src/test/java/com/github/mictaege/arete/CalculatorGherkinStyleSpec.java) for a complete example.

### Descriptive style

```Java
class CalculatorDescriptiveStyleSpec {

    @Describe
    class ACalculator {
        private Calculator calculator = new Calculator();

        @ItShould void subtract5From10() {
            assertThat(calculator.subtract(10, 5), is(5));
        }
    }
}
```
A specification written in descriptive style contains one or more descriptions as nested classes. Each description defines several executable expectations.

See [source](/src/test/java/com/github/mictaege/arete/CalculatorDescriptiveStyleSpec.java) for a complete example.

## Lifecycle and Scope

In Gerkhin style each _Scenario_ is a single test instance (`@TestInstance(PER_CLASS)`) and all steps are sharing the same test instance, and it's state. In this way, one step can access the results of another step. But this also means that the order in which the steps are being executed is important.

In descriptive style a _Description_ is always a new test instance (`@TestInstance(PER_METHOD)`) and all expectations has there exclusive test instance. In this way, every expectation is independent and does not rely on the execution order. But this also means that, one expectation can not access the results of another expectation.

## Execution Order in Gerkhin Style

### Default order of Given-When-Then Sequences

```Java
@Scenario
class ShouldAddFiveToTen {
    int a, b, c;

    @Given void fiveAndTen() {
        a = 5;
        b = 10;
    }

    @When void addingTogether() {
        c = calculator.add(a, b);
    }

    @Then void theResultShouldBeCorrect() {
        assertThat(c, is(15));
    }
}
```
In a simple _Scenario_ the Given-When-Then sequences are automatically ordered, so that the _Given_ sequence is always executed first, followed by the _When_ sequence, and the _Then_ sequence at last. This would even be the case if the order in the source code is different.

### Ordering within a Sequence

```Java
@Scenario
class ShouldAddFiveToTen {
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

    @Then(2) void notBeAnEqualNumber() {
        assertThat(c % 2, is(1));
    }
}
```
When a _Given_, _When_ or _Then_ sequence consists of more than one step, the inner ordering within the sequence has to be defined using the `value` or `step` attribute of the annotation.

### Ordering across consecutive Sequences

```Java
@Scenario
class ShouldAddThreeToTheResultOfFiveAndTen {
    int a, b, c, d;
    @Given void fivAndTen() {
        a = 5;
        b = 10;
    }
    
    @When(seq = 1) void addingFiveToTen() {
        c = calculator.add(a, b);
    }
    
    @Then(seq = 1) void theResultShouldBeCorrect() {
        assertThat(c, is(15));
    }
    @When(seq = 2) void addingThree() {
        d = calculator.add(c, 3);
    }
    @Then(seq = 2, step = 1) void theFinalResultShouldBeCorrect() {
        assertThat(d, is(18));
    }
    @Then(seq = 2, step = 2) void beAnEqualNumber() {
        assertThat(d % 2, is(0));
    }
}
```
If a specification consists of several consecutive Given-When-Then sequences, the sequence and/or the inner order within the sequence may has to be defined using the `seq` and/or `step` attribute.

## Display Name Generation

Per default Arete uses a display name generation strategy that expects class and method names written in _camelCase_ or tokens separated by _under_scores_.

Example:

```Java
@Scenario
class ShouldAddFiveToTen {    
    @Given(1) void five() {...}
    @Given(2) void ten() {...}

    @When void adding_together() {...}

    @Then(1) void theResultShouldBeCorrect() {...}
    @Then(2) void not_be_an_equal_number() {...}
}
```
will result in

```
Scenario: Should Add Five To Ten
    Given five
    And ten
    When adding together
    Then the result should be correct
    And not be an equal number
```
It can happen that the result may not always be optimal. In this case the display name could be explicitly defined using the `desc` attribute of the Arete annotations.

```Java
@Scenario(desc = "Scenario: Should add 5 to 10")
class ShouldAddFiveToTen {
    ...
}
```
