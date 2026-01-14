package com.github.mictaege.arete;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

public abstract class ExampleCsvSource {

    private char delimiter = ',';
    private String csvData = "";
    private final List<ExampleGridRow> rows;

    protected ExampleCsvSource() {
        rows = new ArrayList<>();
        init();
    }

    public final List<ExampleGridRow> getRows() {
        if (rows.isEmpty()) {
            parseCsv();
        }
        return rows;
    }

    protected abstract void init();

    public char getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(final char delimiter) {
        this.delimiter = delimiter;
    }

    public String getCsvData() {
        return csvData;
    }

    public void setCsvData(final String csvData) {
        this.csvData = csvData;
    }

    private void parseCsv() {
        try (final CSVParser parser = CSVParser.parse(getCsvData(), CSVFormat.newFormat(getDelimiter()))) {
            parser.getRecords().forEach(r -> {
                rows.add(new ExampleGridRow(r.toList().stream().map(s -> new ExampleGridData<String>(s.trim(), identity())).collect(toList())));
            });
        } catch (final IOException e) {
            throw new RuntimeException("Unable to parse CSV data", e);
        }
    }

}
