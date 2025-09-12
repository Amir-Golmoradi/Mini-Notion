package dev.amirgol.springtaskbackend.core.constants;

import java.util.Set;
import java.util.regex.Pattern;

public final class Constants {
    public static final Set<String> SUPPORTED_MIME_TYPES = Set.of(
            "image/png", "image/jpeg", "application/pdf", "application/docx"
    );
    public static final Set<String> SUPPORTED_EXTENSIONS = Set.of(
            "png", "jpeg", "jpg", "pdf", "docx"
    );
    public static final int MINIMUM_NAME_LENGTH = 5;
    public static final int MAXIMUM_NAME_LENGTH = 100;

    public static final int MIN_KEY_LENGTH = 3;
    public static final int MAX_KEY_LENGTH = 255;

    public static final Pattern INVALID_CHARS_PATTERN = Pattern.compile("[^a-zA-Z0-9._-]");
    public static final Pattern HAS_INVALID_CHARS_PATTERN = Pattern.compile(".*[/\\\\:*?\"<>|].*");

    public static final Pattern OBJECT_KEY_PATTERN = Pattern.compile("^[a-z0-9-]+(/[a-z0-9._-]+)*$");
}
