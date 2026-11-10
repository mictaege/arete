# Step Context Specification

## Purpose

Let any step body attach arbitrary files (HTML dumps, API responses, logs, HAR files) to the
currently executing step, published as JUnit file attachments under the `arete-attach.` namespace
so the arete-gradle plugin can render them next to that step in the report.

## Requirements

### Requirement: StepContext exposes the current step and attaches files

`StepContext` SHALL provide `static StepContext current()`, throwing `IllegalStateException` when
no step is executing on the calling thread, and an instance method
`attach(String name, MediaType mediaType, byte[] bytes)` that publishes a JUnit file attachment
named `arete-attach.<name>` (constant `ATTACHMENT_NAME_PREFIX = "arete-attach"`) on the current
step's `ExtensionContext` via `publishFile`. Path separators (`/`, `\`) in `name` SHALL be replaced
with `_` before publishing.

#### Scenario: Attach from a step body
- **WHEN** a `@Step`/`@Given`/`@When`/`@Then`/`@ItShould` method calls
  `StepContext.current().attach("response.json", MediaType.APPLICATION_JSON, bytes)`
- **THEN** a JUnit file attachment `arete-attach.response.json` exists for that step's uniqueId

#### Scenario: Called outside a step
- **WHEN** `StepContext.current()` is called outside step execution
- **THEN** an `IllegalStateException` is thrown

#### Scenario: Name sanitization
- **WHEN** `attach("../evil.txt", ...)` is called
- **THEN** the published file is named `arete-attach.._evil.txt`

### Requirement: StepContextExtension holds the current step during execution

`StepContextExtension` (an `InvocationInterceptor` registered via `@ExtendWith` on the step
annotations `@Step`, `@Given`, `@When`, `@Then`, `@ItShould`) SHALL make the step's
`ExtensionContext` available to `StepContext.current()` for the duration of the test method
invocation and SHALL clear it afterwards, also when the step throws.

#### Scenario: Holder cleared after the step
- **WHEN** a step invocation finishes (successfully or exceptionally)
- **THEN** `StepContext.current()` throws `IllegalStateException` on that thread afterwards
