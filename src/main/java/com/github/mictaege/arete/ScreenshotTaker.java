package com.github.mictaege.arete;

import static com.github.mictaege.arete.ScreenshotTaker.TestResult.FAILURE;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.extension.ExtensionContext;

public interface ScreenshotTaker extends Normalizer<ExtensionContext, String> {

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
        final File tmpDir = new File(new File(System.getProperty("java.io.tmpdir")), "arete_screenshots");
        tmpDir.mkdirs();
        return tmpDir;
    }

    default File getScreenshotFile(final ExtensionContext extensionContext) {
        return new File(getScreenshotsDir(), normalize(extensionContext) + "." + getFileExtension());
    }

}
