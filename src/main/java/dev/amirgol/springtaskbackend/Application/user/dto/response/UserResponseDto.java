package dev.amirgol.springtaskbackend.Application.user.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserResponseDto(
        @NotBlank(message = "Name is required and cannot be empty")
        @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters long")
        String name,

        @NotBlank(message = "Email is required and cannot be empty")
        @Email(message = "Email must be a valid address, e.g., user@example.com")
        String email
) {
}
