# Scenarios Specification

## Purpose

Gherkin-style specifications: a `@Spec` class contains nested `@Scenario` classes (optionally
grouped in `@Feature` classes), each defining executable `@Given`/`@When`/`@Then` steps. Scenarios
are `@Nested` with `@TestInstance(PER_CLASS)`: all steps of a scenario share one instance and its
state, so later steps can use results of earlier steps, and execution order matters.

## Requirements

### Requirement: Spec structure and grouping

A `@Spec` class SHALL be a JUnit test container whose nested `@Scenario` classes are executed as
tests; `@Feature` classes SHALL group related scenarios and journeys. `@Spec`, `@Scenario` and
`@Feature` SHALL each have `order()` (default 1), `value()` shorthand and `desc()` attributes,
applied by `AreteOrderer` for class and method ordering.

#### Scenario: Scenarios grouped in features
- **WHEN** a `@Spec` contains a `@Feature` with two `@Scenario` classes
- **THEN** both scenarios execute as part of the spec, grouped under the feature

### Requirement: Default Given-When-Then sequence order

Within a simple scenario, steps SHALL execute in phase order — all `@Given`, then all `@When`, then
all `@Then` — regardless of their source order.

#### Scenario: Source order differs from execution order
- **WHEN** a `@Then` method is declared before the `@When` method in a scenario
- **THEN** the `@When` step still executes before the `@Then` step

### Requirement: Ordering within and across sequences

Steps within one phase SHALL be ordered by `step` (or shorthand `value`); several consecutive
Given-When-Then sequences SHALL be ordered by `seq` (Given/When/Then with the same `seq` form one
sequence, executed before sequences with higher `seq`).

#### Scenario: Multiple sequences
- **WHEN** a scenario declares `@When(seq = 1)`/`@Then(seq = 1)` and later `@When(seq = 2)`/`@Then(seq = 2)`
- **THEN** the whole seq-1 sequence (including its `@Then`) completes before seq 2 starts

### Requirement: Shared scenario state

All steps of a scenario SHALL share one test instance (`PER_CLASS`), so a field set in a `@When`
step SHALL be readable in the `@Then` steps.

#### Scenario: Then reads When's result
- **WHEN** a `@When` step stores a result in a field and a `@Then` step asserts on it
- **THEN** the assertion sees the stored value
