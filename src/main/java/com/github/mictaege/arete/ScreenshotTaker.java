package com.github.mictaege.arete;

import static com.github.mictaege.arete.ScreenshotTaker.TestResult.FAILURE;

import java.io.File;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.extension.ExtensionContext;

public interface ScreenshotTaker extends Normalizer<ExtensionContext, String> {

    String SCREENSHOT_NAME_PREFIX = "arete-screenshot";

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

    default String getScreenshotName() {
        return SCREENSHOT_NAME_PREFIX + "." + getFileExtension();
    }

    default MediaType getMediaType() {
        return switch (getFileExtension().toLowerCase(Locale.ROOT)) {
            case "png" -> MediaType.IMAGE_PNG;
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }

    @Deprecated(forRemoval = true)
    default File getScreenshotsDir() {
        final File screenshotsDir = new File(new File(System.getProperty("java.io.tmpdir")), "arete_screenshots");
        screenshotsDir.mkdirs();
        return screenshotsDir;
    }

    @Deprecated(forRemoval = true)
    default File getScreenshotFile(final ExtensionContext extensionContext) {
        return new File(getScreenshotsDir(), normalize(extensionContext) + "." + getFileExtension());
    }

}
