package com.github.mictaege.arete;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.junit.jupiter.api.io.TempDir;

class StepContextTest {

    @TempDir
    Path tempDir;

    @Test
    void currentThrowsWhenNoStepIsExecuting() {
        assertThrows(IllegalStateException.class, StepContext::current);
    }

    @Test
    void attachPublishesFileWithAttachPrefix() throws Exception {
        final ExtensionContext context = mock(ExtensionContext.class);
        final byte[] bytes = new byte[] {4, 5, 6};
        final Path publishedFile = tempDir.resolve("arete-attach.response.json");
        doAnswer(invocation -> {
            final ThrowingConsumer<Path> writer = invocation.getArgument(2);
            writer.accept(publishedFile);
            return null;
        }).when(context).publishFile(eq("arete-attach.response.json"), eq(MediaType.APPLICATION_JSON), any());

        StepContext.setCurrent(context);
        try {
            StepContext.current().attach("response.json", MediaType.APPLICATION_JSON, bytes);
        } finally {
            StepContext.clearCurrent();
        }

        assertArrayEquals(bytes, Files.readAllBytes(publishedFile));
        verify(context).publishFile(eq("arete-attach.response.json"), eq(MediaType.APPLICATION_JSON), any());
    }

    @Test
    void sanitizeStripsPathSeparators() {
        assertEquals(".._evil.txt", StepContext.sanitize("../evil.txt"));
        assertEquals("a_b_c.txt", StepContext.sanitize("a/b\\c.txt"));
        assertEquals("plain.txt", StepContext.sanitize("plain.txt"));
    }

}
