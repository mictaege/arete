package com.github.mictaege.arete;

import static com.github.mictaege.arete.ScreenshotTaker.TestResult.FAILURE;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import org.junit.jupiter.api.MediaType;

public interface ScreenshotTaker {

    String SCREENSHOT_NAME_PREFIX = "arete-screenshot";

    enum TestResult {
        DISABLED, SUCCESS, ABORTION, FAILURE
    }

    default Set<TestResult> takeWhen() {
        return Collections.singleton(FAILURE);
    }

    byte[] getImageBytes();

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

}
