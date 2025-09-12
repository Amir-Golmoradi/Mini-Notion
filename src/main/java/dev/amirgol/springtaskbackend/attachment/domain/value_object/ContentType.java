package dev.amirgol.springtaskbackend.attachment.domain.value_object;

import dev.amirgol.springtaskbackend.core.common.ValueObject;
import dev.amirgol.springtaskbackend.core.constants.Constants;
import dev.amirgol.springtaskbackend.core.exception.NullOrBlankException;
import dev.amirgol.springtaskbackend.core.exception.UnsupportedValueException;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Value Object for Content Type (MIME type).
 * Enforces validation and categorization for supported attachment types.
 */

@Getter
public final class ContentType extends ValueObject {
    private final String value;

    public ContentType(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new NullOrBlankException(value);
        }

        if (!Constants.SUPPORTED_MIME_TYPES.contains(value)) {
            throw new UnsupportedValueException("Content-Type", value, Constants.SUPPORTED_MIME_TYPES);
        }

        this.value = value;
    }

    public static ContentType of(String value) {
        return new ContentType(value);
    }

    public boolean isImage() {
        return value.startsWith("image/");
    }

    public boolean isDocument() {
        return value.startsWith("application/");
    }


    @Override
    public Collection<Object> getAtomicValues() {
        return List.of(value);
    }
}
