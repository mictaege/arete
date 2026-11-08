# Display Names Specification

## Purpose

Arete generates display names from class and method names written in camelCase or with
under_scores. Generated names feed the JUnit display names and the report. The `desc` attribute of
any arete annotation overrides the generated name.

## Requirements

### Requirement: Name generation from camelCase and under_scores

The system SHALL split class/method names on camelCase boundaries and under_scores and join the
words with spaces (e.g. `ShouldAddFiveToTen` → `Should Add Five To Ten`,
`adding_together` → `adding together`).

#### Scenario: CamelCase scenario name
- **WHEN** a `@Scenario` class is named `ShouldAddFiveToTen`
- **THEN** its display name is `Scenario: Should Add Five To Ten`

### Requirement: Step connectors in generated names

Step display names SHALL be prefixed by their role connector: `Given …`, `When …`, `Then …` in
scenarios (repeated steps in a phase become `And …`), and plain step names in journeys.

#### Scenario: And-connector for repeated Then steps
- **WHEN** a scenario has two `@Then` methods
- **THEN** they display as `Then …` and `And …`

### Requirement: desc overrides the generated name

The `desc` attribute of any arete annotation SHALL replace the generated display name when set.

#### Scenario: Explicit description
- **WHEN** `@Scenario(desc = "Scenario: Should add 5 to 10")` is used
- **THEN** the display name is exactly `Scenario: Should add 5 to 10`
