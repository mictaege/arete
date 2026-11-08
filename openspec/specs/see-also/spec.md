# SeeAlso Specification

## Purpose

`@SeeAlso` links specifications, features, descriptions and scenarios to each other, to describe
relationships between them. Both source and target can be a top-level specification or any nested
feature, description or scenario. The arete-gradle report renders these links on spec pages.

## Requirements

### Requirement: Repeatable class references

`@SeeAlso(SomeSpec.class)` SHALL be repeatable (via the `@SeeAlsoDeclaration` container) and
applicable to types. The target SHALL be a spec class or a nested class of one.

#### Scenario: Link to a nested scenario
- **WHEN** a `@Scenario` is annotated `@SeeAlso(CalculatorSpec.AdditionFeature.ShouldAddTwoNumbers.class)`
- **THEN** the report renders a link from that scenario to the target scenario's page/anchor
