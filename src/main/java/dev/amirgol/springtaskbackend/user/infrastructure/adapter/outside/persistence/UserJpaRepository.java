package dev.amirgol.springtaskbackend.user.infrastructure.adapter.outside.persistence;

import dev.amirgol.springtaskbackend.user.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Short> {
    boolean existsByName(String name);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    Page<User> findUsersByNameAndEmail(String name, String email, Pageable pageable);

}
