# Nesting Specification

## Purpose

Grouping of specification parts: in Gherkin and journey style, `@Feature` classes group related
scenarios and journeys inside a `@Spec`; in descriptive style, `@Describe` classes nest (arbitrarily
deep) and group `@ItShould` expectations. Nesting is how larger specifications stay readable, and it
drives the outline tree of the report.

## Requirements

### Requirement: @Feature groups scenarios and journeys

A `@Spec` MAY contain `@Feature` classes, each containing `@Scenario` and/or `@Journey`
classes. `@Feature` SHALL have `order()` (default 1), `value()` shorthand and `desc()`, applied by
`AreteOrderer`.

#### Scenario: Features in one spec
- **WHEN** a `@Spec` has `@Feature Addition` with two scenarios and `@Feature Subtraction` with one
  scenario and one journey
- **THEN** all are executed as nested parts of the spec, grouped under their feature in the report
  outline

### Requirement: @Describe nests arbitrarily in descriptive style

`@Describe` classes MAY be nested inside `@Spec` and inside other `@Describe` classes; leaf
`@ItShould` methods are the expectations. Nesting depth SHALL map to the report outline levels.

#### Scenario: Two-level describe
- **WHEN** `@Spec` → `@Describe ACalculator` → `@Describe Subtraction` → `@ItShould subtract5From10`
- **THEN** the report shows the spec, the description, the nested description and the expectation as
  nested outline nodes
