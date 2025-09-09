package dev.amirgol.springtaskbackend.user.application.dto.mapper;

import dev.amirgol.springtaskbackend.user.application.dto.response.PageInfo;
import dev.amirgol.springtaskbackend.user.application.dto.response.UserPage;
import dev.amirgol.springtaskbackend.user.application.dto.response.UserResponseDto;
import dev.amirgol.springtaskbackend.core.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.function.Function;

@Component
public class UserGraphQLMapper implements Function<Page<UserResponseDto>, UserPage> {
    @Override
    public UserPage apply(Page<UserResponseDto> page) {
        if (page == null || page.isEmpty()) {
            throw new ResourceNotFoundException("Empty Pagination");
        }
        return UserPage
                .builder()
                .content(Collections.emptyList())
                .pageInfo(
                        PageInfo
                                .builder()
                                .totalElements(0L)
                                .totalPages(0)
                                .pageNumber(0)
                                .pageSize(0)
                                .build())
                .build();
    }
}
