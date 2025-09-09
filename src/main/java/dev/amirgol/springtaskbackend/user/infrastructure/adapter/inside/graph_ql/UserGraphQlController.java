package dev.amirgol.springtaskbackend.user.infrastructure.adapter.inside.graph_ql;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import dev.amirgol.springtaskbackend.user.application.dto.mapper.UserGraphQLMapper;
import dev.amirgol.springtaskbackend.user.application.dto.request.UserRequestDto;
import dev.amirgol.springtaskbackend.user.application.dto.response.UserPage;
import dev.amirgol.springtaskbackend.user.application.dto.response.UserResponseDto;
import dev.amirgol.springtaskbackend.user.application.port.inside.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
@DgsComponent
public record UserGraphQlController(UserService userService, UserGraphQLMapper qlMapper) {
    @DgsQuery
    public UserPage findAllUsers(
            @InputArgument @Min(0) Integer page,
            @InputArgument @Min(1) @Max(100) Integer size
    ) {
        log.debug("GraphQL findAllUsers - page: {}, size: {}", page, size);

        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 10
        );

        var requestDto = createDefaultSearchCriteria();
        var userPage = userService.findAllUsers(requestDto, pageable)
                .orElse(Page.empty());

        return qlMapper.apply(userPage);
    }

    @DgsQuery
    public UserResponseDto findUserById(@InputArgument String id) {
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
    @DgsQuery
    public UserResponseDto findUserByEmail(@InputArgument String email) {
        log.debug("GraphQL findUserByEmail - email: {}", email);
        return userService.findUserByEmail(email);
    }

    /**
     * Finds a user by their name.
     *
     * @param name User name
     * @return User if found, null otherwise
     */
    @DgsQuery
    public UserResponseDto findUserByName(@InputArgument String name) {
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
    @DgsMutation
    public UserResponseDto createUser(@InputArgument("input") @Valid UserRequestDto input) {
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
    @DgsMutation
    public UserResponseDto editUser(
            @InputArgument String email,
            @InputArgument("input") @Valid UserRequestDto input
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
                .passwordHash("passwordHash12345")
                .build();
    }
}
