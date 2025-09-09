package dev.amirgol.springtaskbackend.user.application.dto.mapper;

import dev.amirgol.springtaskbackend.user.application.dto.response.UserResponseDto;
import dev.amirgol.springtaskbackend.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserDtoMapper implements Function<User, UserResponseDto> {
    @Override
    public UserResponseDto apply(User user) {
        return UserResponseDto
                .builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
