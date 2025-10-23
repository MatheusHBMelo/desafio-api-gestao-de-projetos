package dev.matheushbmelo.gestao_api.controller.exceptions;

import dev.matheushbmelo.gestao_api.service.exceptions.ProjectNotFoundException;
import dev.matheushbmelo.gestao_api.service.exceptions.StatusNotFoundException;
import dev.matheushbmelo.gestao_api.service.exceptions.TaskNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<StandardError> taskNotFound(TaskNotFoundException ex, HttpServletRequest http) {
        StandardError error = new StandardError(
                HttpStatus.NOT_FOUND.toString(),
                "Tarefa não encontrada",
                ex.getMessage(),
                http.getServletPath(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<StandardError> projectNotFound(ProjectNotFoundException ex, HttpServletRequest http) {
        StandardError error = new StandardError(
                HttpStatus.NOT_FOUND.toString(),
                "Projeto não encontrado",
                ex.getMessage(),
                http.getServletPath(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(StatusNotFoundException.class)
    public ResponseEntity<StandardError> statusNotFound(StatusNotFoundException ex, HttpServletRequest http) {
        StandardError error = new StandardError(
                HttpStatus.BAD_REQUEST.toString(),
                "Erro no status da tarefa",
                ex.getMessage(),
                http.getServletPath(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            fieldErrors.put(field, message);
        });

        String errorDetails = fieldErrors.toString();

        StandardError error = new StandardError(
                HttpStatus.BAD_REQUEST.toString(),
                "Erro de validação nos campos",
                errorDetails,
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        StandardError error = new StandardError(
                HttpStatus.BAD_REQUEST.toString(),
                "Erro na leitura do corpo da requisição",
                "Verifique se o JSON está bem formatado ou se os tipos dos campos estão corretos",
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
