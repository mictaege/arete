# AGENTS.md — arete

## Project purpose
`arete` is a lightweight JUnit 6 extension that enables a BDD (Behaviour Driven Development) testing
style in Java by describing test cases as specifications and scenarios. It supports several
authoring styles (Gherkin-like, descriptive, "journey") on top of standard JUnit Jupiter classes,
using annotations such as `@Spec` and `@Scenario`.

This is the **core library** of the arete family. The companion project `arete-gradle` builds on top
of it to provide Gradle-native HTML reporting for arete specifications, and `arete-gradle-test`
is a sample/integration-test consumer of that plugin.

## Technical stack
- Language: Java (library sources under `src/main/java`, tests under `src/test/java`).
- Build tool: Gradle (Kotlin DSL, `build.gradle.kts`), using the Gradle wrapper — always invoke
  builds via `./gradlew`, never a globally installed Gradle.
- Packaging: `java-library` plugin, published as a standard JVM library artifact.
- Testing: JUnit Jupiter (the library is itself a JUnit extension, and its own test suite is the
  primary way to validate new features/behaviour).
- Release/publishing: `org.jreleaser` Gradle plugin, publishing to Maven Central under group
  `io.github.mictaege`. Credentials/secrets for signing and Central Portal are passed as Gradle
  properties (`centralPortalUsr`, `centralPortalPwd`, `centralPortalKeyPwd`, etc.) — never hardcode
  or commit these.
- License: Apache License 2.0.

## Project semantics / domain notes
- Package root: `com.github...` (mirrors the GitHub-hosted origin of the project).
- Public API surface centers around JUnit 6 extension points (`@Spec`, `@Scenario`, and related
  annotations/extension classes) — changes here are binary/source-compatibility sensitive for
  consumers such as `arete-gradle`.
- The README documents multiple specification styles (Gherkin, descriptive, journey); when adding
  features, keep parity across these styles where it makes sense.

## Working conventions
- Run the full test suite with `./gradlew test` before considering a change complete.
- Keep the public extension API stable; `arete-gradle` depends on this library's behaviour to
  generate reports, so behavioural changes here can ripple into that project.
- Favor the Gradle Kotlin DSL style already used in `build.gradle.kts` for any build changes.
