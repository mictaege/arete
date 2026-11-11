# Narrative Context Specification

## Purpose

Let the class-level lifecycle of a `@Narrative`-annotated class (constructor, `@BeforeAll`,
`@AfterAll`) attach arbitrary files (aggregated results, generated manifests, build output) to the
current narrative, published as JUnit file attachments under the `arete-narrative-attach.`
namespace so the arete-gradle plugin can render them inside that narrative in the report.

## Requirements

### Requirement: NarrativeContext exposes the current narrative and attaches files

`NarrativeContext` SHALL provide `static NarrativeContext current()`, throwing
`IllegalStateException` when no narrative scope is active on the calling thread, and an instance
method `attach(String name, MediaType mediaType, byte[] bytes)` that publishes a JUnit file
attachment named `arete-narrative-attach.<name>` (constant
`ATTACHMENT_NAME_PREFIX = "arete-narrative-attach"`) on the class-level `ExtensionContext` via
`publishFile`. Path separators (`/`, `\\`) in `name` SHALL be replaced with `_` before publishing.

#### Scenario: Attach from a narrative lifecycle method
- **WHEN** an `@AfterAll` method of a `@Narrative`-annotated class calls
  `NarrativeContext.current().attach("summary.json", MediaType.APPLICATION_JSON, bytes)`
- **THEN** a JUnit file attachment `arete-narrative-attach.summary.json` exists for that class's
  uniqueId

#### Scenario: Called outside a narrative scope
- **WHEN** `NarrativeContext.current()` is called outside the class-level lifecycle of a
  `@Narrative` class (e.g. inside a test method)
- **THEN** an `IllegalStateException` is thrown

#### Scenario: Name sanitization
- **WHEN** `attach("../evil.txt", ...)` is called
- **THEN** the published file is named `arete-narrative-attach.._evil.txt`

### Requirement: NarrativeContextExtension holds the current narrative during the class-level lifecycle

`NarrativeContextExtension` (an `InvocationInterceptor` registered via `@ExtendWith` meta-annotated
on `@Narrative`) SHALL make the class-level `ExtensionContext` available to
`NarrativeContext.current()` for the duration of the test-class constructor, `@BeforeAll` and
`@AfterAll` invocations and SHALL clear it afterwards, also when the invocation throws.

#### Scenario: Holder cleared after the lifecycle method
- **WHEN** a `@BeforeAll`/`@AfterAll`/constructor invocation finishes (successfully or
  exceptionally)
- **THEN** `NarrativeContext.current()` throws `IllegalStateException` on that thread afterwards
