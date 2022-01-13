package com.github.mictaege.arete;

import static java.util.Optional.ofNullable;

import java.util.function.Function;

public class ExampleParam<T> {

    private final T value;
    private final Function<T, String> stringConverter;

    ExampleParam(final T value, final Function<T, String> stringConverter) {
        super();
        this.value = value;
        this.stringConverter = stringConverter;
    }

    ExampleParam(final T value) {
        this(value, T::toString);
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return ofNullable(value).map(stringConverter).orElse("");
    }
}
