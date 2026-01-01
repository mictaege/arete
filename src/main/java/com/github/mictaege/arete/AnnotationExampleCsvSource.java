package com.github.mictaege.arete;

class AnnotationExampleCsvSource extends ExampleCsvSource {

    AnnotationExampleCsvSource(final char delimiter,
                               final String csvData) {
        setDelimiter(delimiter);
        setCsvData(csvData);
    }

    @Override
    protected void init() {
    }
}
