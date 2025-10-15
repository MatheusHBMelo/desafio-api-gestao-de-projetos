package dev.matheushbmelo.gestao_api.controller.project.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ProjectRequestDto(@NotBlank(message = "O nome é obrigatorio") String name, String description, LocalDate endDate) {
}
