package com.github.mictaege.arete;

import java.io.File;

import org.junit.jupiter.api.extension.ExtensionContext;

public interface ScreenshotTaker extends Normalizer<ExtensionContext, String> {

    byte[] getImageBytes();

    @Override
    default String normalize(final ExtensionContext extensionContext) {
        return new UniqueIdToHashNormalizer().normalize(extensionContext.getUniqueId());
    }

    String getFileExtension();

    default File getScreenshotsDir() {
        final File tmpDir = new File(new File(System.getProperty("java.io.tmpdir")), "arete_screenshots");
        tmpDir.mkdirs();
        return tmpDir;
    }

    default File getScreenshotFile(final ExtensionContext extensionContext) {
        return new File(getScreenshotsDir(), normalize(extensionContext) + "." + getFileExtension());
    }

}
