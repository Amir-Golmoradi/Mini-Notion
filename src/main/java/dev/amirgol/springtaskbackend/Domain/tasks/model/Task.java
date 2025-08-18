package dev.amirgol.springtaskbackend.Domain.tasks.model;

import dev.amirgol.springtaskbackend.Domain.category.model.Category;
import dev.amirgol.springtaskbackend.Domain.tasks.enums.TaskStatus;
import dev.amirgol.springtaskbackend.Domain.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

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
    @Column(nullable = false, length = 150)
    private String title;

    @NotNull
    @Column(nullable = false, length = 150)
    private String subtitle;

    @NotNull
    @Column(columnDefinition = "VARCHAR", length = 250)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "task_status default 'PENDING'")
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
