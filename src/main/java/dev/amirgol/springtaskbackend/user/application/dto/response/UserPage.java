package dev.amirgol.springtaskbackend.user.application.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserPage(
        List<UserResponseDto> content,
        PageInfo pageInfo
) {
}

