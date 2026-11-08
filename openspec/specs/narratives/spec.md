# Narratives Specification

## Purpose

`@Narrative` documents user stories, requirements or background information on specifications,
features, scenarios, journeys and descriptions. It supports plain/markdown text, embedded images,
PlantUML diagrams and file attachments.

## Requirements

### Requirement: Narrative text with markdown

`@Narrative` SHALL have `header()` (default `"Narrative:"`) and `value()` (String array of lines).
Values MAY use basic markdown (emphasis, lists); the report renders it as HTML.

#### Scenario: Markdown narrative
- **WHEN** `@Narrative` value contains `*emphasis*` and a `- list` line
- **THEN** the report renders the emphasis and the list

### Requirement: Embedded resources

`@Narrative` SHALL support `imageResourcePath` (images from the classpath), `plantUml` (PlantUML
diagram sources rendered server-side in the report) and `attachmentResourcePath` (any files,
e.g. PDFs, copied into the report and linked).

#### Scenario: Image and attachment
- **WHEN** `imageResourcePath = "com/example/room1.png"` and `attachmentResourcePath = "com/example/invoice.pdf"`
- **THEN** the report shows the image and offers the PDF as a downloadable attachment
