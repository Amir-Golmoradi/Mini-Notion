package dev.amirgol.springtaskbackend.attachment.application.port.inside;

import lombok.Builder;

@Builder
public record AttachmentMetadata(String objectName, long size, String contentType) {}
