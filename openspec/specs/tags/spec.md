# Tags Specification

## Purpose

Arete uses JUnit tags to group and classify specifications, journeys, scenarios, descriptions and
other nested parts. Plain `@Tag` can be used directly; domain-specific tag annotations are composed
with `@Tag` plus `@StereoType` to classify business concepts (actors, entities, workflows, …).
The arete-gradle report lists tags on its tag page for searching/filtering.

## Requirements

### Requirement: Tags on specification parts

Any `@Tag("...")` SHALL be allowed on a spec or nested part and SHALL be visible to consumers
(e.g. the arete-gradle report's tag page).

#### Scenario: Plain tag
- **WHEN** a `@Scenario` is annotated `@Tag("smoke")`
- **THEN** the tag `smoke` is associated with that scenario in test metadata and the report

### Requirement: Domain tags via composed annotations with stereotype

A composed annotation SHALL be definable as `@Target(TYPE) @Retention(RUNTIME) @Tag("traveller")
@StereoType(ACTOR) public @interface Traveller {}` and used on specs/scenarios. `StereoTypes`
SHALL include ACTOR, DOMAIN, UNIT, WORKFLOW, ENTITY, EXTERNAL, EVENT, SCHEDULE, RULE, TAG.

#### Scenario: Composed domain tag
- **WHEN** a spec is annotated with a composed `@Traveller` annotation (stereotype ACTOR)
- **THEN** the report classifies it under the Actors tag group
