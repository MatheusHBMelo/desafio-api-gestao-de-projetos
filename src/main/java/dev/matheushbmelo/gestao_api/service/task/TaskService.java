package dev.matheushbmelo.gestao_api.service.task;

import dev.matheushbmelo.gestao_api.controller.task.dto.StatusDto;
import dev.matheushbmelo.gestao_api.controller.task.dto.TaskRequestDto;
import dev.matheushbmelo.gestao_api.controller.task.dto.TaskResponseDto;
import dev.matheushbmelo.gestao_api.entity.project.ProjectEntity;
import dev.matheushbmelo.gestao_api.entity.task.Priority;
import dev.matheushbmelo.gestao_api.entity.task.Status;
import dev.matheushbmelo.gestao_api.entity.task.TaskEntity;
import dev.matheushbmelo.gestao_api.mapper.task.TaskMapper;
import dev.matheushbmelo.gestao_api.repository.project.ProjectRepository;
import dev.matheushbmelo.gestao_api.repository.task.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
    }

    public void create(@Valid TaskRequestDto taskRequestDto) {
        TaskEntity newTask = taskMapper.mapToTaskEntity(taskRequestDto);
        newTask.setStatus(Status.TODO);
        ProjectEntity project = this.projectRepository.findById(taskRequestDto.projectId())
                .orElseThrow(() -> new RuntimeException("Não existe projeto com ID: " + taskRequestDto.projectId()));
        newTask.setProjectId(project);
        this.taskRepository.save(newTask);
    }

    public List<TaskResponseDto> findByFilter(String status, String priority, Long projectId) {
        Status statusEnum = Status.valueOf(status.toUpperCase());
        Priority priorityEnum = Priority.valueOf(priority.toUpperCase());
        List<TaskEntity> tasks = this.taskRepository.findByFilters(statusEnum, priorityEnum, projectId);
        return taskMapper.mapToTaskResponseDtoList(tasks);
    }

    public void updateStatus(Long id, StatusDto statusDto) {
        TaskEntity task = this.taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Essa task não existe"));
        task.setStatus(Status.valueOf(statusDto.status()));
        this.taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (this.taskRepository.existsById(id)){
            this.taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Essa task não existe");
        }
    }
}
