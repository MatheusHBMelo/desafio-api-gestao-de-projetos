package dev.matheushbmelo.gestao_api.controller.task;

import dev.matheushbmelo.gestao_api.controller.task.dto.TaskRequestDto;
import dev.matheushbmelo.gestao_api.service.task.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody @Valid TaskRequestDto taskRequestDto) {
        this.taskService.create(taskRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
