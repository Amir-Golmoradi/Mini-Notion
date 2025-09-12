package dev.amirgol.springtaskbackend.attachment.domain.model;

import dev.amirgol.springtaskbackend.attachment.domain.value_object.*;
import dev.amirgol.springtaskbackend.task.domain.model.Task;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * This entity stores only metadata about the attachment.
 * The actual file content is not persisted in the database, as storing large binary data
 * (BLOBs) can lead to performance and scalability issues.
 * Instead, the file is stored in an external object storage (e.g., MinIO/AWS S3),
 * and this entity keeps the reference (object key), size, content type, and other metadata.
 */

@Getter
public  class Attachment {
    private final AttachmentId id;
    private final AttachmentSize size;
    private final AttachmentName name;
    private final ObjectKey objectKey;
    private final ContentType contentType;
    private final LocalDateTime createdAt;
    private final Task task;
    private LocalDateTime updatedAt;

    public Attachment(AttachmentId id, AttachmentSize size, AttachmentName name, ObjectKey objectKey, ContentType contentType, LocalDateTime createdAt, Task task) {
        this.id = id;
        this.size = size;
        this.name = name;
        this.objectKey = objectKey;
        this.contentType = contentType;
        this.createdAt = createdAt;
        this.task = task;
    }
}
