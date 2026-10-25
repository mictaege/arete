package com.github.mictaege.arete;

import static com.github.mictaege.arete.ScreenshotTaker.TestResult.FAILURE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.junit.jupiter.api.io.TempDir;

class ScreenshotExtensionTest {

    @TempDir
    Path tempDir;

    @Test
    void publishesImageAsJUnitFileEntry() throws Exception {
        final ExtensionContext context = mock(ExtensionContext.class);
        final byte[] imageBytes = new byte[] {1, 2, 3};
        final Path publishedFile = tempDir.resolve("arete-screenshot.png");
        final ScreenshotTaker screenshotTaker = new ScreenshotTaker() {
            @Override
            public byte[] getImageBytes() {
                return imageBytes;
            }
        };
        doAnswer(invocation -> {
            final ThrowingConsumer<Path> writer = invocation.getArgument(2);
            writer.accept(publishedFile);
            return null;
        }).when(context).publishFile(eq("arete-screenshot.png"), eq(MediaType.IMAGE_PNG), any());

        new ScreenshotExtension(screenshotTaker).testFailed(context, new AssertionError("failed"));

        assertArrayEquals(imageBytes, Files.readAllBytes(publishedFile));
        verify(context).publishFile(eq("arete-screenshot.png"), eq(MediaType.IMAGE_PNG), any());
    }

    @Test
    void doesNotPublishWhenResultIsNotConfigured() {
        final ExtensionContext context = mock(ExtensionContext.class);
        final ScreenshotTaker screenshotTaker = mock(ScreenshotTaker.class);
        when(screenshotTaker.takeWhen()).thenReturn(Set.of(FAILURE));

        new ScreenshotExtension(screenshotTaker).testSuccessful(context);

        verify(screenshotTaker, never()).getImageBytes();
        verifyNoInteractions(context);
    }

    @Test
    void doesNotPublishEmptyImageBytes() {
        final ExtensionContext context = mock(ExtensionContext.class);
        final ScreenshotTaker screenshotTaker = mock(ScreenshotTaker.class);
        when(screenshotTaker.takeWhen()).thenReturn(Set.of(FAILURE));
        when(screenshotTaker.getImageBytes()).thenReturn(new byte[0]);

        new ScreenshotExtension(screenshotTaker).testFailed(context, new AssertionError("failed"));

        verifyNoInteractions(context);
    }
}