package dev.matheushbmelo.gestao_api.controller.task.dto;

import dev.matheushbmelo.gestao_api.entity.task.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskRequestDto(@NotBlank String title, String description, Priority priority, LocalDate dueDate, @NotNull Long projectId) {
}
