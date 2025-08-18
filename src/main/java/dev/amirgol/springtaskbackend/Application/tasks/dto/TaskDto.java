package dev.amirgol.springtaskbackend.Application.tasks.dto;

import dev.amirgol.springtaskbackend.Domain.tasks.enums.TaskStatus;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

public record TaskDto(
        short id,
        @NotNull @Size(max = 150)
        String title,
        @NotNull @Size(max = 150)
        String subtitle,
        @NotNull @Size(max = 250)
        String description,
        TaskStatus status
) {
}
