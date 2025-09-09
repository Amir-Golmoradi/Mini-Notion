package dev.amirgol.springtaskbackend.attachment.application.usecase;

public record FindAttachment(
        String bucketName,
        String objectName
) {
}


