package dev.amirgol.springtaskbackend.user.application.port.outside;

import dev.amirgol.springtaskbackend.user.application.dto.mapper.UserDtoMapper;
import dev.amirgol.springtaskbackend.user.application.dto.request.UserRequestDto;
import dev.amirgol.springtaskbackend.user.application.dto.response.UserResponseDto;
import dev.amirgol.springtaskbackend.user.application.port.inside.UserService;
import dev.amirgol.springtaskbackend.user.domain.model.User;
import dev.amirgol.springtaskbackend.user.domain.repository.UserRepository;
import dev.amirgol.springtaskbackend.core.exception.ResourceAlreadyExistsException;
import dev.amirgol.springtaskbackend.core.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserDtoMapper mapper;
    private final UserRepository userRepository;

    @Override
    public Optional<Page<UserResponseDto>> findAllUsers(UserRequestDto requestDto, Pageable pageable) {
        log.debug("Finding all users ...");
        Page<User> userPage = userRepository.findUsersByNameAndEmail(
                requestDto.name(),
                requestDto.email(),
                pageable
        );
        log.debug("Found {} users", userPage.getTotalElements());
        return Optional.of(userPage.map(mapper));
    }

    @Override
    public UserResponseDto findUserById(Short id) {
        log.info("Finding user by id {}", id);
        return userRepository.findUserById(id)
                .map(mapper)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public UserResponseDto findUserByEmail(String email) {
        log.info("Finding user by email {}", email);

        if (!userRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException("User with email " + email + " not found");
        }
        return userRepository.findUsersByEmail(email)
                .map(mapper)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }

    @Override
    public UserResponseDto findUserByName(String username) {
        if (!userRepository.existsByName(username)) {
            throw new ResourceNotFoundException("User with name " + username + " not found");
        }
        return userRepository.findUsersByName(username)
                .map(mapper)
                .orElseThrow(() -> new ResourceNotFoundException("User with name " + username + " not found"));
    }

    @Override
    @Transactional
    public UserResponseDto registerUser(UserRequestDto requestDto) {
        log.debug("Registering new user with email: {}", requestDto.email());
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new ResourceAlreadyExistsException("User with email " + requestDto.email() + " already exists");
        }
        // 1. Map UserRequestDto to User entity
        var user = buildUserFromRequest(requestDto);
        // 3. Save the user
        User savedUser = userRepository.saveUser(user);
        // 4. Return UserResponseDto (without password)
        log.info("Successfully registered user with email: {}", savedUser.getEmail());
        return mapper.apply(savedUser);
    }

    @Override
    @Transactional
    public UserResponseDto editUser(String email, UserRequestDto requestDto) {
        // 1. Finding Existing User
        var existingUser = userRepository.findUsersByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " does not exist"));

        updateUserFiled(existingUser, requestDto);
        var savedUser = userRepository.saveUser(existingUser);
        log.info("Successfully updated user with email: {}", email);

        return mapper.apply(savedUser);
    }

    // ======================= HELPER METHODS =======================
    private User buildUserFromRequest(UserRequestDto requestDto) {
        User user = new User();
        user.setName(requestDto.name());
        user.setEmail(requestDto.email());
        user.setPasswordHash(requestDto.passwordHash());
        return user;
    }

    private void updateUserFiled(User existingUser, UserRequestDto requestDto) {
        if (nonNull(requestDto.name())) {
            existingUser.setName(requestDto.name());
        }

        if (nonNull(requestDto.email())) {
            existingUser.setEmail(requestDto.email());
        }

        if (nonNull(requestDto.passwordHash()) && !requestDto.passwordHash().isEmpty()) {
            var encodedPassword = requestDto.passwordHash();
            existingUser.setPasswordHash(encodedPassword);
        }
    }

}
