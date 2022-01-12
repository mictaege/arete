package com.github.mictaege.arete;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Arrays;

public class Calculator {

    public int add(final Integer... a) {
        return Arrays.stream(a).reduce(Integer::sum).orElse(0);
    }

    public int subtract(final Integer... a) {
        return Arrays.stream(a).reduce((x,y) -> x - y).orElse(0);
    }

    public Integer multiply(final Integer a, final Integer b) {
        return a * b;
    }

    public double divide(final Integer a, final Integer b) {
        checkArgument(b != 0);
        return (((double)a/(double)b));
    }

}
