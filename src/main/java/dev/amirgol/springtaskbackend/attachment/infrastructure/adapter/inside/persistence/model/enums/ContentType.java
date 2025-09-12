package dev.amirgol.springtaskbackend.attachment.infrastructure.adapter.inside.persistence.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContentType {
    PNG("image/png"),
    JPEG("image/jpeg"),
    PDF("application/pdf"),
    DOCX("application/vnd.openxmlformats-officedocument.wordprocessingml.document")


    private final String mimeType;


}
