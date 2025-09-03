package dev.amirgol.springtaskbackend.Application.user.dto.response;

import lombok.Builder;

@Builder
public record PageInfo(
        long totalElements,
        int totalPages,
        int pageNumber,
        int pageSize
) {
}
