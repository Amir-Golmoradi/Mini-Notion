package dev.amirgol.springtaskbackend.task.application.dto;

import dev.amirgol.springtaskbackend.task.domain.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskDto(
        short id,
        @NotNull
        @Size(min = 4, max = 250)
        @NotBlank(message = "Title is required")
        String title,
        @NotNull
        @Size(min = 4, max = 250)
        String subtitle,
        @NotNull
        @Size(max = 250)
        String description,
        @NotNull
        TaskStatus status
) {
}
