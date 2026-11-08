# Lifecycle Specification

## Purpose

Execution order and visibility controls across the authoring styles: `AreteOrderer` applies the
`order()`/`value()` attributes of arete container annotations for both class and method ordering;
`@AreteOrder` bridges plain JUnit `@Order`; `@HiddenIfDisabled` hides disabled steps from reports.

## Requirements

### Requirement: Ordering by order/value attributes

`@Spec`, `@Scenario`, `@Feature`, `@Journey`, `@VariableJourney`, `@Describe` and the step
annotations SHALL be ordered by `AreteOrderer` (registered via `@TestClassOrder`/`@TestMethodOrder`
on the annotations) using `order()` (shorthand `value()`), so containers and steps execute in
attribute order rather than source order.

#### Scenario: Scenarios run in declared order
- **WHEN** two `@Scenario` classes have `order` 2 and 1 (declared in reverse)
- **THEN** the order-1 scenario executes first

### Requirement: @AreteOrder bridges JUnit @Order

`@AreteOrder` SHALL allow plain `org.junit.jupiter.api.Order` semantics alongside arete's
`order()`/`value()` attributes.

#### Scenario: Mixed ordering annotations
- **WHEN** an element uses `@AreteOrder` with a JUnit `@Order` value
- **THEN** the element is ordered by that value like other ordered elements

### Requirement: HiddenIfDisabled hides disabled steps from reports

An element annotated `@HiddenIfDisabled` SHALL be excluded from the generated report when its test
is disabled (the arete-gradle report renders no markup for it).

#### Scenario: Disabled scenario hidden
- **WHEN** a `@Scenario` is `@Disabled` and annotated `@HiddenIfDisabled`
- **THEN** the arete report contains no entry for that scenario
