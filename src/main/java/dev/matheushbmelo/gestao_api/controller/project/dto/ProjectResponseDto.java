package dev.matheushbmelo.gestao_api.controller.project.dto;

import java.time.LocalDateTime;

public record ProjectResponseDto(Long id, String name, String description, LocalDateTime startDate) {
}
