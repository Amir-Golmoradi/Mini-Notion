package dev.amirgol.springtaskbackend.Domain.attachment.model;

import dev.amirgol.springtaskbackend.Domain.tasks.model.Task;
import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Column(nullable = false, length = 100)
    private String fileName;

    @Column(nullable = false, length = 255)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
}
