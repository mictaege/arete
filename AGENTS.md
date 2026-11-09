# AGENTS.md — arete

> New to the arete family? Read the onboarding guide in the family store first:
> https://github.com/mictaege/arete-openspec (README.md).

Project context and conventions live in OpenSpec: `openspec/config.yaml` (project context) and
`openspec/specs/<capability>/spec.md` (capability specs). Use the OpenSpec workflow for behavior
changes: propose via `/openspec-propose`, implement via `/openspec-apply-change`, validate with
`openspec validate`, finish with `/openspec-archive-change`.

## Read the relevant spec before changing

- Gherkin style (`@Spec`/`@Scenario`/`@Given`/`@When`/`@Then`, ordering): `openspec/specs/scenarios/spec.md`
- Journey style (`@Journey`/`@Step`, `@VariableJourney` variants): `openspec/specs/journeys/spec.md`
- Descriptive style (`@Describe`/`@ItShould`): `openspec/specs/descriptions/spec.md`
- Examples (`@Examples`/`@ExampleGrid`/`@ExampleCsv`): `openspec/specs/examples/spec.md`
- `@Narrative`: `openspec/specs/narratives/spec.md`; tags/`@StereoType`: `openspec/specs/tags/spec.md`
- `@Ref`/`@Refs`: `openspec/specs/refs/spec.md`; `@SeeAlso`: `openspec/specs/see-also/spec.md`
- Display name generation: `openspec/specs/display-names/spec.md`
- Screenshots (`ScreenshotExtension`/`ScreenshotTaker`): `openspec/specs/screenshots/spec.md`
- Lifecycle/ordering (`AreteOrderer`, `AreteOrder`): `openspec/specs/lifecycle/spec.md`
- Nesting/grouping (`@Feature`, `@Describe`): `openspec/specs/nesting/spec.md`
- Internals (`Normalizer`/`UniqueIdToHashNormalizer`, `InnerClassUtils`): `openspec/specs/internals/spec.md`

## Working conventions

- Run the full test suite with `./gradlew test` before considering a change complete.
- Keep the public extension API stable; `arete-gradle` depends on this library's behaviour to
  generate reports, so behavioural changes here can ripple into that project.
- Favor the Gradle Kotlin DSL style already used in `build.gradle.kts` for any build changes.
- Behavior changes to the public API or execution semantics require an OpenSpec change proposal;
  typo-level fixes do not.
