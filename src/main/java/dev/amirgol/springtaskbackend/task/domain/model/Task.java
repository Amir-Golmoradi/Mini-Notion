package dev.amirgol.springtaskbackend.task.domain.model;

import dev.amirgol.springtaskbackend.category.domain.model.Category;
import dev.amirgol.springtaskbackend.task.domain.enums.TaskStatus;
import dev.amirgol.springtaskbackend.user.domain.model.User;
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

    @Builder.Default // Fix 1: Add @Builder.Default to use the initializing expression
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDING;

    @Builder.Default // Fix 2: Add @Builder.Default to use the initializing expression
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isDeleted = false;

    @Builder.Default // Fix 3: Add @Builder.Default to use the initializing expression
    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Builder.Default // Fix 4: Add @Builder.Default to use the initializing expression
    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    // Fix 5: Remove @Builder.Default. The 'user' relationship must be set explicitly.
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

//    @Builder.Default // Fix 6: Add @Builder.Default to use the initializing expression
//    @ManyToMany
//    @JoinTable(
//            name = "task_categories",
//            joinColumns = @JoinColumn(name = "task_id"),
//            inverseJoinColumns = @JoinColumn(name = "category_id")
//    )
//    private List<Category> categories = new ArrayList<>();

    @PreUpdate
    public void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}