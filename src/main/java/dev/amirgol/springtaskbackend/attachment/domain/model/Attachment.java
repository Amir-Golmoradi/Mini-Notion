package dev.amirgol.springtaskbackend.attachment.domain.model;

import dev.amirgol.springtaskbackend.task.domain.model.Task;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This entity stores only metadata about the attachment.
 * The actual file content is not persisted in the database, as storing large binary data
 * (BLOBs) can lead to performance and scalability issues.
 * Instead, the file is stored in an external object storage (e.g., MinIO/AWS S3),
 * and this entity keeps the reference (object key), size, content type, and other metadata.
 */


@Builder
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private String objectKey;
    
    @Column(nullable = false)
    private String contentType;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        this.createdAt = this.updatedAt = LocalDateTime.now();
    }


    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
