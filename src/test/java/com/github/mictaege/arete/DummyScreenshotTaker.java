package com.github.mictaege.arete;

import java.io.File;

import org.junit.jupiter.api.extension.ExtensionContext;

import com.google.common.io.Files;

class DummyScreenshotTaker implements ScreenshotTaker {

    @Override
    public byte[] getImageBytes() {
        try {
            return Files.asByteSource(new File(getClass().getResource("Dummy.png").toURI())).read();
        } catch (final Exception e) {
            return new byte[0];
        }
    }

    @Override
    public String normalize(final ExtensionContext context) {
        return context.getParent().map(p -> p.getTestClass().map(Class::getName).map(n -> n.replace(".", "_")).orElse("")).orElse("") + "_"
                + context.getDisplayName().replace(" ", "_");
    }
}
