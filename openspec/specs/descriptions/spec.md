# Descriptions Specification

## Purpose

Descriptive style: a `@Spec` contains nested `@Describe` classes (nestable), each defining
`@ItShould` expectation methods. In contrast to scenarios and journeys, a description is
`@TestInstance(PER_METHOD)`: every expectation gets its own exclusive test instance, so expectations
are independent and order-independent, but cannot share state.

## Requirements

### Requirement: Nested descriptions with independent expectations

`@Describe` classes SHALL be `@Nested` containers that can nest further `@Describe` classes, each
holding `@ItShould` methods. `@Describe` has `order()` (default 1), `value()` (default 2) and
`desc()`; `@ItShould` has `order()`/`value()`/`desc()`.

#### Scenario: Expectations are isolated
- **WHEN** a `@Describe` class has two `@ItShould` methods that both mutate an instance field
- **THEN** each expectation runs on a fresh instance and cannot observe the other's state
