package dev.amirgol.springtaskbackend.user.domain.repository;

import dev.amirgol.springtaskbackend.user.domain.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {
    Page<User> findUsersByNameAndEmail(@NotBlank String name, @Email String email, Pageable pageable);

    Optional<User> findUserById(Short id);

    User saveUser(User user);

    boolean existsByName(@NotBlank(message = "Username is required") @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters") String name);

    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email);

    Optional<User> findUsersByEmail(@Email String email);

    Optional<User> findUsersByName(String name);
}
