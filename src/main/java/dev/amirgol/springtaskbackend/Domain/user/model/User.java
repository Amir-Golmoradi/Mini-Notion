package dev.amirgol.springtaskbackend.Domain.user.model;

import dev.amirgol.springtaskbackend.Domain.tasks.model.Task;
import dev.amirgol.springtaskbackend.Domain.user.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
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

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;  // SMALL SERIAL

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String passwordHash;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Account status flags for security control
     */
    @Column(name = "account_non_expired")
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired = true;

    @Column(name = "enabled")
    private boolean enabled = true;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    /**
     * Returns the authorities granted to the user
     * In our case, we use role-based authority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert role to Spring Security authority format (ROLE_prefix)
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    /**
     * UserDetails implementation methods
     * These methods are used by Spring Security for authentication decisions
     */
    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
