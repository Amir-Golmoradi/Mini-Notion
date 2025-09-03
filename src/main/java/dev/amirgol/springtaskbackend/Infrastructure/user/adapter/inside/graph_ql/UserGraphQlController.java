package dev.amirgol.springtaskbackend.Infrastructure.user.adapter.inside.graph_ql;

import dev.amirgol.springtaskbackend.Application.user.dto.mapper.UserGraphQLMapper;
import dev.amirgol.springtaskbackend.Application.user.dto.request.UserRequestDto;
import dev.amirgol.springtaskbackend.Application.user.dto.response.UserPage;
import dev.amirgol.springtaskbackend.Application.user.dto.response.UserResponseDto;
import dev.amirgol.springtaskbackend.Application.user.port.inside.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserGraphQlController {
    private final UserService userService;
    private final UserGraphQLMapper qlMapper;

    @QueryMapping
    public UserPage findAllUsers(
            @Argument @Min(0) Integer page,
            @Argument @Min(1) @Max(100) Integer size
    ) {
        log.debug("GraphQL findAllUsers - page: {}, size: {}", page, size);

        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 10
        );

        var requestDto = createDefaultSearchCriteria();
        var userPage = userService.findAllUsers(requestDto, pageable)
                .orElse(org.springframework.data.domain.Page.empty());

        return qlMapper.apply(userPage);
    }

    @QueryMapping
    public UserResponseDto findUserById(@Argument String id) {
        log.debug("GraphQL findUserById - {}", id);

        try {
            Short userId = Short.valueOf(id);
            return userService.findUserById(userId);
        } catch (NumberFormatException e) {
            log.warn("Invalid user ID format: {}", id);
            return null;
        }
    }



    /**
     * Finds a user by their email address.
     *
     * @param email User email
     * @return User if found, null otherwise
     */
    @QueryMapping
    public UserResponseDto findUserByEmail(@Argument String email) {
        log.debug("GraphQL findUserByEmail - email: {}", email);
        return userService.findUserByEmail(email);
    }

    /**
     * Finds a user by their name.
     *
     * @param name User name
     * @return User if found, null otherwise
     */
    @QueryMapping
    public UserResponseDto findUserByName(@Argument String name) {
        log.debug("GraphQL findUserByName - name: {}", name);
        return userService.findUserByName(name);
    }

    // ===================== MUTATION OPERATIONS =====================

    /**
     * Creates a new user.
     * Uses UserRequestDto directly as GraphQL input.
     *
     * @param input User creation data (UserRequestDto)
     * @return Created user
     */
    @MutationMapping
    public UserResponseDto createUser(@Argument("input") @Valid UserRequestDto input) {
        log.debug("GraphQL createUser - email: {}", input.email());
        return userService.registerUser(input);
    }

    /**
     * Updates an existing user by email.
     * Uses UserRequestDto directly as GraphQL input.
     *
     * @param email Target user email
     * @param input Updated user data (UserRequestDto)
     * @return Updated user
     */
    @MutationMapping
    public UserResponseDto editUser(
            @Argument String email,
            @Argument("input") @Valid UserRequestDto input
    ) {
        log.debug("GraphQL editUser - email: {}", email);
        return userService.editUser(email, input);
    }



    // ===================== PRIVATE HELPER METHODS =====================

    /**
     * Creates default search criteria for findAllUsers query.
     * Matches the default behavior of the REST controller.
     */
    private UserRequestDto createDefaultSearchCriteria() {
        return UserRequestDto.builder()
                .name("Mike Tyson")
                .email("miketyson@boxing.com")
                .passwordHash("defaulthash")
                .build();
    }
}
