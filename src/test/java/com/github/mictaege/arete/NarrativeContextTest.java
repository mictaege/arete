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

class NarrativeContextTest {

    @TempDir
    Path tempDir;

    @Test
    void currentThrowsWhenNoNarrativeIsExecuting() {
        assertThrows(IllegalStateException.class, NarrativeContext::current);
    }

    @Test
    void attachPublishesFileWithNarrativeAttachPrefix() throws Exception {
        final ExtensionContext context = mock(ExtensionContext.class);
        final byte[] bytes = new byte[] {7, 8, 9};
        final Path publishedFile = tempDir.resolve("arete-narrative-attach.summary.json");
        doAnswer(invocation -> {
            final ThrowingConsumer<Path> writer = invocation.getArgument(2);
            writer.accept(publishedFile);
            return null;
        }).when(context).publishFile(eq("arete-narrative-attach.summary.json"), eq(MediaType.APPLICATION_JSON), any());

        NarrativeContext.setCurrent(context);
        try {
            NarrativeContext.current().attach("summary.json", MediaType.APPLICATION_JSON, bytes);
        } finally {
            NarrativeContext.clearCurrent();
        }

        assertArrayEquals(bytes, Files.readAllBytes(publishedFile));
        verify(context).publishFile(eq("arete-narrative-attach.summary.json"), eq(MediaType.APPLICATION_JSON), any());
    }

    @Test
    void sanitizeStripsPathSeparators() {
        assertEquals(".._evil.txt", NarrativeContext.sanitize("../evil.txt"));
        assertEquals("a_b_c.txt", NarrativeContext.sanitize("a/b\\c.txt"));
        assertEquals("plain.txt", NarrativeContext.sanitize("plain.txt"));
    }

}
