# Refs Specification

## Purpose

`@Ref` links specifications and nested parts (features, descriptions, scenarios, journeys) to
external references — e.g. requirements, user stories or issues of a tracking system — for
traceability. This spec defines the annotation API in `arete`; the report rendering is specified in
arete-gradle's `openspec/specs/refs/spec.md`.

## Requirements

### Requirement: Repeatable `@Ref` annotation

`@Ref` SHALL be applicable to types and annotation types, runtime-retained and repeatable via the
`@Refs` container. Attributes: `value` (the reference id, e.g. `"PROJ-123"` or an issue number;
default `""` so composed annotations can be meta-annotated without a value), `url` (default `""`).

#### Scenario: Multiple references on one element
- **WHEN** an element is annotated `@Ref("A-1") @Ref("A-2")` (compiled into `@Refs`)
- **THEN** both references are readable via `getAnnotationsByType(Ref.class)`

### Requirement: Url pattern semantics

Consumers SHALL resolve a reference's link from `url` and `value`: a `{0}` placeholder in `url` is
replaced by the `value`; without a placeholder the `url` is used as a prefix and the `value` is
appended; without a `url` the reference has no link.

#### Scenario: Placeholder and prefix forms
- **WHEN** `@Ref(value = "14", url = "https://github.com/x/y/issues/{0}")` or
  `@Ref(value = "16", url = "https://github.com/x/y/issues/")` is used
- **THEN** the resolved links are `.../issues/14` and `.../issues/16` respectively

### Requirement: Composed reference annotations

An annotation type MAY be meta-annotated with `@Ref(url = ...)` (e.g. `@GithubRef` with
`@Ref(url = "https://github.com/mictaege/arete-gradle/issues/{0}")` and its own `value()`), staying
repeatable via its own container (e.g. `@GithubRefs`). Consumers SHALL read the composed
annotation's own `value()` as the reference value and resolve the meta `@Ref`'s url pattern with it.

#### Scenario: Composed annotation resolves to issue URL
- **WHEN** `@GithubRef("14")` is used
- **THEN** consumers resolve the reference `14` with link
  `https://github.com/mictaege/arete-gradle/issues/14`
