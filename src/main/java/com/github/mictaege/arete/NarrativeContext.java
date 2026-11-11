package com.github.mictaege.arete;

import java.nio.file.Files;

import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Access to the current narrative, e.g. to attach files to it.
 *
 * <p>Reachable from the class-level lifecycle of a {@link Narrative}-annotated class
 * ({@code @BeforeAll}, {@code @AfterAll}, constructor) via {@link #current()}:
 * <pre>{@code
 * NarrativeContext.current().attach("summary.json", MediaType.APPLICATION_JSON, bytes);
 * }</pre>
 */
public final class NarrativeContext {

    public static final String ATTACHMENT_NAME_PREFIX = "arete-narrative-attach";

    private static final ThreadLocal<ExtensionContext> CURRENT = new ThreadLocal<>();

    private final ExtensionContext context;

    private NarrativeContext(final ExtensionContext context) {
        this.context = context;
    }

    /**
     * @return the context of the narrative currently executing on this thread
     * @throws IllegalStateException if no narrative scope is active on this thread
     */
    public static NarrativeContext current() {
        final ExtensionContext context = CURRENT.get();
        if (context == null) {
            throw new IllegalStateException("No narrative is currently executing on this thread");
        }
        return new NarrativeContext(context);
    }

    static void setCurrent(final ExtensionContext context) {
        CURRENT.set(context);
    }

    static void clearCurrent() {
        CURRENT.remove();
    }

    /**
     * Attaches a file to the current narrative. Published via JUnit {@code publishFile} under the
     * {@value #ATTACHMENT_NAME_PREFIX} namespace, distinct from step attachments and screenshots.
     *
     * @param name file name; path separators are replaced with {@code _}
     * @param mediaType the media type of the file
     * @param bytes the file content
     */
    public void attach(final String name, final MediaType mediaType, final byte[] bytes) {
        context.publishFile(
                ATTACHMENT_NAME_PREFIX + "." + sanitize(name),
                mediaType,
                path -> Files.write(path, bytes));
    }

    static String sanitize(final String name) {
        return name.replaceAll("[/\\\\]", "_");
    }

}
