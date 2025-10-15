package dev.matheushbmelo.gestao_api.controller.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ProjectResponseDto(Long id, String name, String description, @JsonFormat(pattern = "dd/MM/yyyy") LocalDate startDate) {
}
