package dev.matheushbmelo.gestao_api.controller.task.dto;

import dev.matheushbmelo.gestao_api.entity.task.Priority;
import dev.matheushbmelo.gestao_api.entity.task.Status;

import java.time.LocalDate;

public record TaskResponseDto(Long id, String title, String description, Status status, Priority priority,
                              LocalDate dueDate, Long projectId) {
}
