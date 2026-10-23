package com.github.mictaege.arete;

import static com.github.mictaege.arete.ScreenshotTaker.TestResult.FAILURE;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.extension.ExtensionContext;

public interface ScreenshotTaker extends Normalizer<ExtensionContext, String> {

    String SCREENSHOTS_DIR_PROPERTY = "arete.screenshotsDir";

    enum TestResult {
        DISABLED, SUCCESS, ABORTION, FAILURE
    }

    default Set<TestResult> takeWhen() {
        return Collections.singleton(FAILURE);
    }

    byte[] getImageBytes();

    @Override
    default String normalize(final ExtensionContext extensionContext) {
        return new UniqueIdToHashNormalizer().normalize(extensionContext.getUniqueId());
    }

    default String getFileExtension() {
        return "png";
    }

    default File getScreenshotsDir() {
        final String configuredDir = System.getProperty(SCREENSHOTS_DIR_PROPERTY);
        final File screenshotsDir = configuredDir == null || configuredDir.isBlank()
                ? new File(new File(System.getProperty("java.io.tmpdir")), "arete_screenshots")
                : new File(configuredDir);
        screenshotsDir.mkdirs();
        return screenshotsDir;
    }

    default File getScreenshotFile(final ExtensionContext extensionContext) {
        return new File(getScreenshotsDir(), normalize(extensionContext) + "." + getFileExtension());
    }

}
