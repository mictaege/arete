package com.github.mictaege.arete;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class ExampleSource {

    private final List<ExampleParams> examples;

    protected ExampleSource() {
        examples = new ArrayList<>();
        build();
    }

    public final List<ExampleParams> getExamples() {
        return examples;
    }

    protected abstract void build();

    public final void example(final ExampleParam<?>... params) {
        examples.add(new ExampleParams("{0})", params));
    }

    public final void example(final String prefixPattern, final ExampleParam<?>... params) {
        examples.add(new ExampleParams(prefixPattern, params));
    }

    public final <T> ExampleParam<T> expect(final T value, final Function<T, String> stringConverter) {
        return new ExampleParam<T>(value, stringConverter);
    }

    public final <T> ExampleParam<T> expect(final T value, final String string) {
        return new ExampleParam<T>(value, v -> string);
    }

    public final <T> ExampleParam<T> expect(final T value) {
        return new ExampleParam<T>(value);
    }

    public final <T> ExampleParam<T> given(final T value, final Function<T, String> stringConverter) {
        return new ExampleParam<T>(value, stringConverter);
    }

    public final <T> ExampleParam<T> given(final T value, final String string) {
        return new ExampleParam<T>(value, v -> string);
    }

    public final <T> ExampleParam<T> given(final T value) {
        return new ExampleParam<T>(value);
    }

    public final <T> ExampleParam<T> when(final T value, final Function<T, String> stringConverter) {
        return new ExampleParam<T>(value, stringConverter);
    }

    public final <T> ExampleParam<T> when(final T value, final String string) {
        return new ExampleParam<T>(value, v -> string);
    }

    public final <T> ExampleParam<T> when(final T value) {
        return new ExampleParam<T>(value);
    }

    public final <T> ExampleParam<T> then(final T value, final Function<T, String> stringConverter) {
        return new ExampleParam<T>(value, stringConverter);
    }

    public final <T> ExampleParam<T> then(final T value, final String string) {
        return new ExampleParam<T>(value, v -> string);
    }

    public final <T> ExampleParam<T> then(final T value) {
        return new ExampleParam<T>(value);
    }

}
