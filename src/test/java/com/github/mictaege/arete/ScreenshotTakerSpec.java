package com.github.mictaege.arete;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ScreenshotTakerSpec {

    @TempDir
    Path tempDir;

    @Test
    void usesConfiguredScreenshotsDirectory() {
        final String originalDir = System.getProperty(ScreenshotTaker.SCREENSHOTS_DIR_PROPERTY);
        final Path configuredDir = tempDir.resolve("configured");
        try {
            System.setProperty(ScreenshotTaker.SCREENSHOTS_DIR_PROPERTY, configuredDir.toString());

            final var screenshotsDir = new TestScreenshotTaker().getScreenshotsDir();

            assertEquals(configuredDir.toFile(), screenshotsDir);
            assertTrue(screenshotsDir.isDirectory());
        } finally {
            if (originalDir == null) {
                System.clearProperty(ScreenshotTaker.SCREENSHOTS_DIR_PROPERTY);
            } else {
                System.setProperty(ScreenshotTaker.SCREENSHOTS_DIR_PROPERTY, originalDir);
            }
        }
    }

    @Test
    void fallsBackToTemporaryDirectoryWhenNotConfigured() {
        final String originalDir = System.getProperty(ScreenshotTaker.SCREENSHOTS_DIR_PROPERTY);
        final String originalTmpDir = System.getProperty("java.io.tmpdir");
        try {
            System.clearProperty(ScreenshotTaker.SCREENSHOTS_DIR_PROPERTY);
            System.setProperty("java.io.tmpdir", tempDir.toString());

            final var screenshotsDir = new TestScreenshotTaker().getScreenshotsDir();

            assertEquals(tempDir.resolve("arete_screenshots").toFile(), screenshotsDir);
            assertTrue(screenshotsDir.isDirectory());
        } finally {
            if (originalDir == null) {
                System.clearProperty(ScreenshotTaker.SCREENSHOTS_DIR_PROPERTY);
            } else {
                System.setProperty(ScreenshotTaker.SCREENSHOTS_DIR_PROPERTY, originalDir);
            }
            if (originalTmpDir == null) {
                System.clearProperty("java.io.tmpdir");
            } else {
                System.setProperty("java.io.tmpdir", originalTmpDir);
            }
        }
    }

    private static class TestScreenshotTaker implements ScreenshotTaker {
        @Override
        public byte[] getImageBytes() {
            return new byte[0];
        }
    }
}