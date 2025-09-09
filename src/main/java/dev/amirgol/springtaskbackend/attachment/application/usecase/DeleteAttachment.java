package dev.amirgol.springtaskbackend.attachment.application.usecase;

public record DeleteAttachment(
        String bucketName,
        String objectName
) {
}
