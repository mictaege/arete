package com.github.mictaege.arete;

import java.util.List;

public class ExampleGridRow {

    private final ExampleGridData<?>[] data;

    ExampleGridRow(final ExampleGridData<?>... data) {
        this.data = data;
    }

    ExampleGridRow(final List<ExampleGridData<?>> data) {
        this.data = data.toArray(new ExampleGridData[0]);
    }

    public ExampleGridData<?>[] getData() {
        return data;
    }
}
