package com.github.mictaege.arete;

import java.text.MessageFormat;

public class ExampleParams {

    private final String prefixPattern;
    private final ExampleParam<?>[] params;

    ExampleParams(final String prefixPattern, final ExampleParam<?>... params) {
        this.prefixPattern = prefixPattern;
        this.params = params;
    }

    public String getPrefix(final int idx) {
        return MessageFormat.format(prefixPattern, idx);
    }

    public ExampleParam<?>[] getParams() {
        return params;
    }
}
