package dev.amirgol.springtaskbackend.Infrastructure.user.adapter.outside.persistence;

import dev.amirgol.springtaskbackend.Domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Short> {
    boolean existsUserByName(String name);

    boolean existsUserByEmail(String email);

    Optional<User> findUsersByEmail(String email);

    Optional<User> findUsersByName(String name);

    Page<User> findByNameAndEmail(String name, String email, Pageable pageable);
}
