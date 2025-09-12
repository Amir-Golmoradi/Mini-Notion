package dev.amirgol.springtaskbackend.attachment.domain.value_object;

import dev.amirgol.springtaskbackend.core.common.ValueObject;
import dev.amirgol.springtaskbackend.core.constants.Constants;
import dev.amirgol.springtaskbackend.core.exception.LengthOutOfBoundsException;
import dev.amirgol.springtaskbackend.core.exception.NullOrBlankException;
import lombok.Getter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
public final class ObjectKey extends ValueObject {
    private final String key;

    public ObjectKey(String key) {
        this.key = validateAndNormalize(key);

    }


    private String validateAndNormalize(String objectKey) {
        // Check for null or blank input
        if (Objects.isNull(objectKey) || objectKey.trim().isEmpty()) {
            throw new NullOrBlankException("Object key");
        }

        // Normalize the key
        String normalized = normalizeKey(objectKey);

        if (normalized.length() > Constants.MAX_KEY_LENGTH) {
            throw new LengthOutOfBoundsException("Object Key", Constants.MIN_KEY_LENGTH, Constants.MAX_KEY_LENGTH);
        }
        if (normalized.length() < Constants.MIN_KEY_LENGTH) {
            throw new LengthOutOfBoundsException("Object Key", Constants.MIN_KEY_LENGTH, Constants.MAX_KEY_LENGTH);
        }


        // Validate the normalized key
        if (!isKeyValid(normalized)) {
            throw new IllegalStateException(
                    String.format("Object key '%s' is invalid. Allowed pattern: %s", normalized, Constants.OBJECT_KEY_PATTERN.pattern())
            );
        }

        return normalized;
    }

    private String normalizeKey(String objectKey) {
        String normalized = Constants.INVALID_CHARS_PATTERN
                .matcher(objectKey)
                .replaceAll("_")
                .toLowerCase()
                .trim();

        // Remove Duplicate Slashes
        normalized = normalized.replaceAll("/+", "/");

        return normalized;
    }


    private boolean isKeyValid(String objectKey) {
        return Constants.OBJECT_KEY_PATTERN
                .matcher(objectKey).matches() && !objectKey.contains("..");
    }


    public String getPath() {
        if (Objects.isNull(key) || key.isEmpty()) {
            return "";
        }
        int lastSlashIndex = key.lastIndexOf('/');
        if (lastSlashIndex == -1) {
            return "";
        }
        return key.substring(0, lastSlashIndex);
    }

    public String generateVersion(int version) {
        int lastDotIndex = key.lastIndexOf(".");
        int lastSlashIndex = key.lastIndexOf("/");

        String fileNameWithExt;
        if (lastSlashIndex == -1) {
            fileNameWithExt = key;
        } else {
            fileNameWithExt = key.substring(lastSlashIndex + 1);
        }

        String baseName;
        String extension = "";
        if (lastDotIndex != -1) {
            baseName = fileNameWithExt.substring(0, lastDotIndex);
            extension = fileNameWithExt.substring(lastDotIndex);
        } else {
            baseName = fileNameWithExt;
        }

        String versionedFileName = baseName + "_v" + version + extension;

        if (lastSlashIndex == -1) {
            return versionedFileName;
        } else {
            return key.substring(0, lastSlashIndex + 1) + versionedFileName;
        }
    }

    public String toUrl(String baseUrl) {
        if (Objects.isNull(baseUrl) || baseUrl.isEmpty()) {
            throw new NullOrBlankException(baseUrl);
        }
        String normalizedBaseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        String encodedKey = URLEncoder.encode(key, StandardCharsets.UTF_8);

        encodedKey = encodedKey.replace("+", "%20");

        return normalizedBaseUrl + "/" + encodedKey;
    }

    @Override
    public Collection<Object> getAtomicValues() {
        return List.of(key);
    }

}
