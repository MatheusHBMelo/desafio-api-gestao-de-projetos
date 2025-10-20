package dev.matheushbmelo.gestao_api.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardError {
    private String code;
    private String message;
    private String error;
    private String path;
    private LocalDateTime timestamp;
}
