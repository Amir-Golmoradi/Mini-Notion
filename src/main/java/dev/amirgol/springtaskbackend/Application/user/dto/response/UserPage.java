package dev.amirgol.springtaskbackend.Application.user.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserPage(
        List<UserResponseDto> content,
        PageInfo pageInfo
) {
}

