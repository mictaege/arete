package com.github.mictaege.arete;

import java.io.File;

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
}
