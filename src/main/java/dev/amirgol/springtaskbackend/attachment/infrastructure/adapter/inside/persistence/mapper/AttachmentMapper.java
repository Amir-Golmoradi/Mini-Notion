package dev.amirgol.springtaskbackend.attachment.infrastructure.adapter.inside.persistence.mapper;

import dev.amirgol.springtaskbackend.attachment.domain.model.Attachment;
import dev.amirgol.springtaskbackend.attachment.infrastructure.adapter.inside.persistence.model.entity.AttachmentEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AttachmentMapper implements Function<Attachment, AttachmentEntity> {
    @Override
    public AttachmentEntity apply(Attachment domain) {
        return AttachmentEntity.builder()
                .id(domain.getId())
                .size(domain.getSize())
                .name(domain.getName())
                .objectKey(domain.getObjectKey())
                .contentType(domain.getContentType())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}
