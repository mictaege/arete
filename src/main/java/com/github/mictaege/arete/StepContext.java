package com.github.mictaege.arete;

import java.nio.file.Files;

import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Access to the currently executing step, e.g. to attach files to it.
 *
 * <p>Reachable from any step body via {@link #current()}:
 * <pre>{@code
 * StepContext.current().attach("response.json", MediaType.APPLICATION_JSON, bytes);
 * }</pre>
 */
public final class StepContext {

    public static final String ATTACHMENT_NAME_PREFIX = "arete-attach";

    private static final ThreadLocal<ExtensionContext> CURRENT = new ThreadLocal<>();

    private final ExtensionContext context;

    private StepContext(final ExtensionContext context) {
        this.context = context;
    }

    /**
     * @return the context of the step currently executing on this thread
     * @throws IllegalStateException if no step is executing on this thread
     */
    public static StepContext current() {
        final ExtensionContext context = CURRENT.get();
        if (context == null) {
            throw new IllegalStateException("No step is currently executing on this thread");
        }
        return new StepContext(context);
    }

    static void setCurrent(final ExtensionContext context) {
        CURRENT.set(context);
    }

    static void clearCurrent() {
        CURRENT.remove();
    }

    /**
     * Attaches a file to the current step. Published via JUnit {@code publishFile} under the
     * {@value #ATTACHMENT_NAME_PREFIX} namespace, distinct from screenshots.
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
