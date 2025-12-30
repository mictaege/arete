package com.github.mictaege.arete;

public class ExampleGridRow {

    private final ExampleGridData<?>[] data;

    ExampleGridRow(final ExampleGridData<?>... data) {
        this.data = data;
    }

    public ExampleGridData<?>[] getData() {
        return data;
    }
}
