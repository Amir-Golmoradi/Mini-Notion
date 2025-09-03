package dev.amirgol.springtaskbackend.Domain.user.model;

import dev.amirgol.springtaskbackend.Domain.tasks.model.Task;
import dev.amirgol.springtaskbackend.Domain.user.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User entity that implements UserDetails for Spring Security integration
 * This entity represents a user in our system and provides authentication details
 */
@Builder
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "unique_email", columnNames = "email")})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;  // SMALL SERIAL

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String passwordHash;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Builder.Default
    @Column(nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

}
