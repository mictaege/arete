package com.github.mictaege.arete;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import com.google.common.io.Files;

public class ScreenshotExtension implements TestWatcher {

    public enum TestResult {
        DISABLED, SUCCESS, ABORTION, FAILURE
    }

    private final ScreenshotTaker screenshotTaker;
    private final Set<TestResult> resultsFilter;

    public ScreenshotExtension(final ScreenshotTaker screenshotTaker) {
        this(screenshotTaker, TestResult.FAILURE);
    }

    public ScreenshotExtension(final ScreenshotTaker screenshotTaker, final TestResult... filterResults) {
        this.screenshotTaker = screenshotTaker;
        this.resultsFilter = Arrays.stream(filterResults).collect(toSet());
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
        if (resultsFilter.contains(result)) {
            ofNullable(screenshotTaker).ifPresent(t -> {
                of(t)
                        .map(ScreenshotTaker::getImageBytes)
                        .filter(b -> b.length > 0)
                        .ifPresent(b -> {
                            try {
                                Files.asByteSink(t.getScreenshotFile(context)).write(b);
                            } catch (final Exception ignore) {
                                //nop
                            }
                        });
            });
        }
    }

}
