package com.github.mictaege.arete;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.stream.Collectors;

class ResourceExampleCsvSource extends ExampleCsvSource {
    
    ResourceExampleCsvSource(final char delimiter,
                             final String csvResourcePath) {
        setDelimiter(delimiter);
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream(csvResourcePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("Resource not found: " + csvResourcePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            final String csvData = reader.lines().collect(Collectors.joining("\n"));
            setCsvData(csvData);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read resource: " + csvResourcePath, e);
        }
    }

    @Override
    protected void init() {
    }
    
}
