package dev.amirgol.springtaskbackend.Infrastructure.user.adapter.outside.persistence;

import dev.amirgol.springtaskbackend.Application.user.dto.mapper.UserDtoMapper;
import dev.amirgol.springtaskbackend.Domain.user.model.User;
import dev.amirgol.springtaskbackend.Domain.user.repository.UserRepository;
import dev.amirgol.springtaskbackend.core.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository jpaRepository;
    private final UserDtoMapper mapper;

    @Override
    public Page<User> findUsersByNameAndEmail(String name, String email, Pageable pageable) {
        return jpaRepository.findUsersByNameAndEmail(name, email, pageable);
    }

    @Override
    public Optional<User> findUserById(Short id) {
        return Optional.of(
                jpaRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"))
        );
    }

    @Override
    public User saveUser(User user) {
        jpaRepository.save(user);
        return user;
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findUsersByEmail(String email) {
        return jpaRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findUsersByName(String name) {
        return jpaRepository.findByName(name);
    }
}
