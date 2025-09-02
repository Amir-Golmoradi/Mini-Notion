package dev.amirgol.springtaskbackend.Unit.Infrastructure.user;

import dev.amirgol.springtaskbackend.Domain.user.model.User;
import dev.amirgol.springtaskbackend.Infrastructure.user.adapter.outside.persistence.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("UserJpaRepository Integration Tests")
class UserRepositoryTest {

    @Autowired
    private UserJpaRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User lucy;

    @BeforeEach
    void setUp() {
        lucy = User.builder()
                .name("Lucy Hale")
                .email("lucyhale@example.com")
                .passwordHash("lucyHale1203")
                .build();
    }

    @Test
    @DisplayName("Should find users by their name and email")
    void findUsersByNameAndEmail() {
        String name = "Lucy Hale";
        String email = "lucyhale@example.com";
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.Direction.ASC, "name");
        Page<User> result = userRepository.findUsersByNameAndEmail(name, email, pageRequest);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo(name);
        assertThat(result.getContent().get(0).getEmail()).isEqualTo(email);
    }


    @Test
    @DisplayName("Should save a user and retrieve it by ID")
    void shouldSaveUserAndRetrieveById() {
        User savedUser = userRepository.save(lucy);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();

        Optional<User> foundUser = userRepository.findById(savedUser.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(savedUser.getEmail());
    }

    @Test
    @DisplayName("Should find a user by email")
    void shouldFindUserByEmail() {
        entityManager.persistAndFlush(lucy);

        Optional<User> foundUser = userRepository.findByEmail(lucy.getEmail());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo(lucy.getEmail());
    }

    @Test
    @DisplayName("Should find users by name")
    void shouldFindUsersByName() {
        entityManager.persistAndFlush(lucy);

        Optional<User> foundedUsers = userRepository.findByName(lucy.getName());
        assertThat(foundedUsers).isPresent();
        assertThat(foundedUsers.get().getName()).isEqualTo(lucy.getName());
    }

    @Test
    @DisplayName("Should check user existence by their Email")
    void shouldCheckUserExistenceByEmail() {
        entityManager.persistAndFlush(lucy);
        var existedUser = userRepository.existsByEmail(lucy.getEmail());
        assertThat(existedUser).isTrue();
    }

    @Test
    @DisplayName("Should check user existence by their Name")
    void shouldCheckUserExistenceByName() {
        entityManager.persistAndFlush(lucy);
        var existedUser = userRepository.existsByName(lucy.getName());
        assertThat(existedUser).isTrue();
    }


    @Test
    @DisplayName("Should return empty when finding a non-existent user by ID")
    void shouldReturnEmptyForNonExistentUserById() {
        Optional<User> foundUser = userRepository.findById(Short.valueOf("999"));
        assertThat(foundUser).isEmpty();
    }


    @Test
    @DisplayName("Should return empty when finding a non-existent user by email")
    void shouldReturnEmptyForNonExistentUserByEmail() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");
        assertThat(foundUser).isEmpty();
    }

    @Test
    @DisplayName("Should return empty when finding a non-existent user by name")
    void shouldReturnEmptyForNonExistentUserByName() {
        Optional<User> foundUser = userRepository.findByName("John Doe");
        assertThat(foundUser).isEmpty();
    }

}
