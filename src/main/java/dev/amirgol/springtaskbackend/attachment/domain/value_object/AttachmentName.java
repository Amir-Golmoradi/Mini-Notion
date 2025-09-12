package dev.amirgol.springtaskbackend.attachment.domain.value_object;

import dev.amirgol.springtaskbackend.core.common.ValueObject;
import dev.amirgol.springtaskbackend.core.constants.Constants;
import dev.amirgol.springtaskbackend.core.exception.InvalidCharactersException;
import dev.amirgol.springtaskbackend.core.exception.LengthOutOfBoundsException;
import dev.amirgol.springtaskbackend.core.exception.NullOrBlankException;
import dev.amirgol.springtaskbackend.core.exception.UnsupportedValueException;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
public final class AttachmentName extends ValueObject {
    private final String fileName;

    public AttachmentName(String fileName) {
        var normalized = normalizeFileName(fileName);
        validateFileName(normalized);
        String extension = extractExtension(normalized);
        if (!isExtensionValid(extension)) {
            throw new UnsupportedValueException(
                    "File Extension",
                    extension,
                    Constants.SUPPORTED_EXTENSIONS
            );
        }
        this.fileName = normalized;
    }


    private String normalizeFileName(String fileName) {

        // Replace all the invalid characters with underscore. // My Video Games From 2012;
        String normalized = Constants.INVALID_CHARS_PATTERN.matcher(fileName).replaceAll("_");

        normalized = normalized
                .replace(" ", "_") // My File Name => My_File_Name
                .trim()
                .replaceAll("^_+|_+$", "")
                .toLowerCase();

        if(Constants.HAS_INVALID_CHARS_PATTERN.matcher(normalized).matches()) {
            throw  new InvalidCharactersException("AttachmentName");
        }

        return normalized;
    }

    private void validateFileName(String fileName) {
        if (Objects.isNull(fileName) || fileName.isBlank()) {
            throw new NullOrBlankException(fileName);
        }
        if (fileName.length() < Constants.MINIMUM_NAME_LENGTH) {
            throw new LengthOutOfBoundsException(
                    fileName,
                    Constants.MINIMUM_NAME_LENGTH,
                    Constants.MAXIMUM_NAME_LENGTH
            );
        }
        if (fileName.length() > Constants.MAXIMUM_NAME_LENGTH) {
            throw new LengthOutOfBoundsException(
                    fileName,
                    Constants.MINIMUM_NAME_LENGTH,
                    Constants.MAXIMUM_NAME_LENGTH
            );
        }
    }

    private String extractExtension(String fileName) {
        if (Objects.isNull(fileName) || fileName.isBlank()) {
            throw new IllegalArgumentException("FileName cannot be null or blank");
        }
        int lastDotIndex = fileName.lastIndexOf('.'); // .

        // There's No Index Before `.` => .gitignore
        if (lastDotIndex <= 0) {
            throw new UnsupportedValueException(
                    "File extension", "", Constants.SUPPORTED_EXTENSIONS
            );
        }

        // There's No Index after `.` => ReadMe. => There's No `md` after `.`
        if (lastDotIndex == fileName.length() - 1) {
            throw new UnsupportedValueException(
                    "File extension",
                    "",
                    Constants.SUPPORTED_EXTENSIONS
            );
        }
        lastDotIndex = lastDotIndex + 1;

        return fileName.substring(lastDotIndex).toLowerCase();
    }

    private boolean isExtensionValid(String extension) {
        return Constants.SUPPORTED_EXTENSIONS.contains(extension.toLowerCase());
    }

    @Override
    public Collection<Object> getAtomicValues() {
        return List.of(fileName);
    }

}
