package dev.matheushbmelo.gestao_api.controller.task.impl;

import dev.matheushbmelo.gestao_api.controller.task.TaskController;
import dev.matheushbmelo.gestao_api.controller.task.dto.StatusDto;
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
public class TaskControllerImpl implements TaskController {
    private final TaskService taskService;

    public TaskControllerImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody @Valid TaskRequestDto taskRequestDto) {
        this.taskService.create(taskRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findByParam(@RequestParam(name = "status", required = true) String status,
                                                             @RequestParam(name = "priority", required = true) String priority,
                                                             @RequestParam(name = "projectId", required = true) Long projectId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.taskService.findByFilter(status, priority, projectId));
    }

    @PutMapping(path = "/{id}/status")
    public ResponseEntity<Void> updateTask(@PathVariable(value = "id") Long id, @RequestBody StatusDto statusDto){
        this.taskService.updateStatus(id, statusDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable(value = "id") Long id) {
        this.taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
