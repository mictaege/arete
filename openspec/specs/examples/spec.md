# Examples Specification

## Purpose

Examples (data-driven tests) extend scenarios and descriptions: a test method annotated with
`@Examples`, `@ExampleGrid` or `@ExampleCsv` is a JUnit `@TestTemplate` whose parameters are bound
per example row from an example source.

## Requirements

### Requirement: `@Examples` test templates

`@Examples` SHALL mark a parameterized method as `@TestTemplate` (via `ExampleCtxProvider`).
Attributes: `pattern` (required, `java.text.MessageFormat` with `{0}`, `{1}`, … replaced by the
example values to describe each invocation), `srcMethod` (a method taking a single `ExampleSource`
parameter) or `srcClass` (a class extending `ExampleSource` with a default constructor), `desc`
(overrides the generated examples description), `order`/`value`.

#### Scenario: Pattern describes each example
- **WHEN** `@Examples(pattern = "{0} + {1} => {2}", srcMethod = "src")` is used with an example
  `given(3), given(4), then(7)`
- **THEN** that invocation is displayed as `3 + 4 => 7`, prefixed with its index (e.g. `1)`)

### Requirement: ExampleSource builder API

`ExampleSource` SHALL provide `given(...)`/`when(...)`/`then(...)`/`expect(...)` builder methods
whose values are bound to the test method parameters in order and type. Each example MAY define a
custom prefix (instead of the index) and a `Function` converting a value to its display text when
`toString` is insufficient.

#### Scenario: Custom prefix and value converter
- **WHEN** an example uses `example("{0}) only positive:", given(3), given(4), then(7))` and a converter
- **THEN** the invocation uses the custom prefix and the converted text in the pattern

### Requirement: Tabular examples

`@ExampleGrid` SHALL take `columns` plus rows from `srcMethod`/`srcClass` (`ExampleGridSource.row(...)`);
`@ExampleCsv` SHALL take `columns`, `delimiter` (default `,`), inline `csvData` and/or
`csvResourcePath`, and binds String parameters only.

#### Scenario: CSV data drives the test
- **WHEN** `@ExampleCsv(columns = {...}, delimiter = '|', csvData = "2026-01-01 | 2026-01-05 | true")`
- **THEN** the test method runs once per CSV row with the row's String values as parameters
