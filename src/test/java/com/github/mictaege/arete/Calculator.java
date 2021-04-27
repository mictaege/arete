package com.github.mictaege.arete;

import static com.google.common.base.Preconditions.checkArgument;

public class Calculator {

    public int add(final int a, final int b) {
        return a + b;
    }

    public int add(final int a, final int b, final int c) {
        return a + b + c;
    }

    public int subtract(final int a, final int b) {
        return a - b;
    }

    public double divide(final int a, final int b) {
        checkArgument(b != 0);
        return (((double)a/(double)b));
    }

}
