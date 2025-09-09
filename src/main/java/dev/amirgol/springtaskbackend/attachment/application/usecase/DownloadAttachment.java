package dev.amirgol.springtaskbackend.attachment.application.usecase;

public record DownloadAttachment(
        String bucketName,
        String objectName
) {
}
