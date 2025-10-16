package dev.matheushbmelo.gestao_api.controller.task;

import dev.matheushbmelo.gestao_api.controller.task.dto.TaskRequestDto;
import dev.matheushbmelo.gestao_api.controller.task.dto.TaskResponseDto;
import dev.matheushbmelo.gestao_api.service.task.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findByParam(@RequestParam(required = true) String status,
                                                             @RequestParam(required = true) String priority,
                                                             @RequestParam(required = true) Long projectId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.taskService.findByFilter(status, priority, projectId));
    }
}
