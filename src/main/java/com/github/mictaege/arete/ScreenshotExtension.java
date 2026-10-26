package com.github.mictaege.arete;

import java.nio.file.Files;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import com.github.mictaege.arete.ScreenshotTaker.TestResult;

public class ScreenshotExtension implements TestWatcher {

    private final ScreenshotTaker screenshotTaker;

    public ScreenshotExtension(final ScreenshotTaker screenshotTaker) {
        this.screenshotTaker = screenshotTaker;
    }

    @Override
    public void testDisabled(final ExtensionContext context, final Optional<String> reason) {
        takeScreenshot(context, TestResult.DISABLED);
    }

    @Override
    public void testSuccessful(final ExtensionContext context) {
        takeScreenshot(context, TestResult.SUCCESS);
    }

    @Override
    public void testAborted(final ExtensionContext context, final Throwable cause) {
        takeScreenshot(context, TestResult.ABORTION);
    }

    @Override
    public void testFailed(final ExtensionContext context, final Throwable cause) {
        takeScreenshot(context, TestResult.FAILURE);
    }

    private void takeScreenshot(final ExtensionContext context, final TestResult result) {
        if (screenshotTaker.takeWhen().contains(result)) {
            try {
                final byte[] imageBytes = screenshotTaker.getImageBytes();
                if (imageBytes.length > 0) {
                    context.publishFile(
                            screenshotTaker.getScreenshotName(),
                            screenshotTaker.getMediaType(),
                            path -> Files.write(path, imageBytes));
                }
            } catch (final Exception ignore) {
                //nop
            }
        }
    }

}
