package dev.amirgol.springtaskbackend.attachment.application.usecase;

import java.io.InputStream;

public record UploadAttachment(
        String bucketName,
        String objectName,
        InputStream data,
        String contentType
) {
}
