# Journeys Specification

## Purpose

Journey style describes a concrete end-to-end flow as an ordered sequence of `@Step` methods inside a
`@Journey` class of a `@Spec` — without splitting into Given/When/Then phases. Useful when the flow
should read like a business process or user journey. Like scenarios, journeys are
`@TestInstance(PER_CLASS)`: all steps share one instance and state, and `@Step` order is explicit.

## Requirements

### Requirement: Ordered steps in a journey

`@Journey` classes SHALL contain `@Step`-annotated methods executed from lowest to highest `order`
(shorthand `value`); `@Journey` has `order()`/`value()`/`desc()` like other containers. A journey
MAY define `@BeforeAll` (once before all steps) and `@BeforeEach` (before each step) hooks.

#### Scenario: Steps execute by order attribute
- **WHEN** a `@Journey` declares `@Step(3)`, `@Step(1)`, `@Step(2)` methods (declared out of order)
- **THEN** they execute in order 1, 2, 3

### Requirement: Phases group steps semantically

`@Step.phase` SHALL be a free-text label that groups steps into named sections for display; it SHALL
NOT affect execution order.

#### Scenario: Phase does not reorder
- **WHEN** two steps have `order` 1 and 2 but different `phase` values
- **THEN** execution still follows `order`, and the report groups them under their phase labels

### Requirement: Variable journeys execute once per variant

A `@VariableJourney` SHALL be executed once for every distinct variant found in the `variant`
attributes of its `@Step` methods. Steps without a `variant` SHALL run in every variant; steps with
a `variant` SHALL run only in the matching variant. `@BeforeAll` SHALL run once before all variants,
`@BeforeVariant`/`@AfterVariant` before/after each variant, `@BeforeEach` before each step.

#### Scenario: Justified and unjustified complaint
- **WHEN** a `@VariableJourney` has common steps plus `@Step(order = 3, variant = "Justified")` and
  `@Step(order = 3, variant = "Unjustified")`
- **THEN** the journey runs twice: once per variant, each time executing the common steps and only
  the matching variant step
