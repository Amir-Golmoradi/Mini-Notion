package dev.amirgol.springtaskbackend.Domain.tasks.model;

import dev.amirgol.springtaskbackend.Domain.category.model.Category;
import dev.amirgol.springtaskbackend.Domain.tasks.enums.TaskStatus;
import dev.amirgol.springtaskbackend.Domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @NotNull
    @Size(min = 4, max = 250)
    @Column(nullable = false, length = 150)
    @NotBlank(message = "Title is required")
    private String title;

    @NotNull
    @Size(min = 4, max = 250)
    @Column(nullable = false, length = 150)
    private String subtitle;

    @NotNull
    @Size(max = 250)
    @Column(nullable = false, length = 250)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDING;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDeleted;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToMany
    @JoinTable(
            name = "task_categories",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();


    @PreUpdate
    public void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
