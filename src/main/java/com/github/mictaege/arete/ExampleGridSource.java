package com.github.mictaege.arete;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class ExampleGridSource {

    private final List<ExampleGridRow> rows;

    protected ExampleGridSource() {
        rows = new ArrayList<>();
        init();
    }

    public final List<ExampleGridRow> getRows() {
        return rows;
    }

    protected abstract void init();

    public final void row(final ExampleGridData<?>... data) {
        rows.add(new ExampleGridRow(data));
    }

    public final <T> ExampleGridData<T> expect(final T value, final Function<T, String> stringConverter) {
        return new ExampleGridData<T>(value, stringConverter);
    }

    public final <T> ExampleGridData<T> expect(final T value, final String string) {
        return new ExampleGridData<T>(value, v -> string);
    }

    public final <T> ExampleGridData<T> expect(final T value) {
        return new ExampleGridData<T>(value);
    }

    public final <T> ExampleGridData<T> given(final T value, final Function<T, String> stringConverter) {
        return new ExampleGridData<T>(value, stringConverter);
    }

    public final <T> ExampleGridData<T> given(final T value, final String string) {
        return new ExampleGridData<T>(value, v -> string);
    }

    public final <T> ExampleGridData<T> given(final T value) {
        return new ExampleGridData<T>(value);
    }

    public final <T> ExampleGridData<T> when(final T value, final Function<T, String> stringConverter) {
        return new ExampleGridData<T>(value, stringConverter);
    }

    public final <T> ExampleGridData<T> when(final T value, final String string) {
        return new ExampleGridData<T>(value, v -> string);
    }

    public final <T> ExampleGridData<T> when(final T value) {
        return new ExampleGridData<T>(value);
    }

    public final <T> ExampleGridData<T> then(final T value, final Function<T, String> stringConverter) {
        return new ExampleGridData<T>(value, stringConverter);
    }

    public final <T> ExampleGridData<T> then(final T value, final String string) {
        return new ExampleGridData<T>(value, v -> string);
    }

    public final <T> ExampleGridData<T> then(final T value) {
        return new ExampleGridData<T>(value);
    }

}
