# Screenshots Specification

## Purpose

Screenshot support for journeys/scenarios: a `ScreenshotTaker` produces image bytes;
`ScreenshotExtension` (a JUnit `TestWatcher` used via `@RegisterExtension`) publishes them as JUnit
file attachments named `arete-screenshot.<ext>`. The arete-gradle plugin (≥ 2026.7) receives these
attachments and copies them into the report and history.

## Requirements

### Requirement: ScreenshotTaker contract

`ScreenshotTaker` SHALL expose `byte[] getImageBytes()`, default `getScreenshotName()` returning
`SCREENSHOT_NAME_PREFIX + "." + getFileExtension()` (`SCREENSHOT_NAME_PREFIX = "arete-screenshot"`),
default `getMediaType()` derived from the extension (png/jpg/jpeg, otherwise octet-stream), and
`takeWhen()` defaulting to `{ FAILURE }` (`TestResult`: DISABLED, SUCCESS, ABORTION, FAILURE).

#### Scenario: Default name and media type
- **WHEN** a `ScreenshotTaker` with `getFileExtension() = "png"` is used
- **THEN** the published file is named `arete-screenshot.png` with media type `image/png`

### Requirement: ScreenshotExtension publishes attachments

`ScreenshotExtension` SHALL, for each test whose result is in `takeWhen()`, call `getImageBytes()`
and, when non-empty, publish via `ExtensionContext.publishFile(getScreenshotName(), getMediaType(),
writer)`. Exceptions SHALL be ignored (as before).

#### Scenario: Failure screenshot published
- **WHEN** a `@RegisterExtension ScreenshotExtension` is present and a step fails with non-empty bytes
- **THEN** a JUnit file attachment `arete-screenshot.*` exists for that test

#### Scenario: Empty bytes are not published
- **WHEN** `getImageBytes()` returns an empty array
- **THEN** no file attachment is published
