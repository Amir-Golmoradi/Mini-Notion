package dev.amirgol.springtaskbackend.attachment.infrastructure.adapter.inside.persistence.model.entity;

import dev.amirgol.springtaskbackend.attachment.infrastructure.adapter.inside.persistence.model.enums.ContentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachments")
public class AttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private int size;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private String objectKey;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private ContentType contentType;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = getUpdatedAt();
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = getCreatedAt();
        this.updatedAt = getUpdatedAt();
    }

}
