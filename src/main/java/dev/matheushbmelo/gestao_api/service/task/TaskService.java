package dev.matheushbmelo.gestao_api.service.task;

import dev.matheushbmelo.gestao_api.controller.task.dto.TaskRequestDto;
import dev.matheushbmelo.gestao_api.entity.project.ProjectEntity;
import dev.matheushbmelo.gestao_api.entity.task.Status;
import dev.matheushbmelo.gestao_api.entity.task.TaskEntity;
import dev.matheushbmelo.gestao_api.mapper.task.TaskMapper;
import dev.matheushbmelo.gestao_api.repository.project.ProjectRepository;
import dev.matheushbmelo.gestao_api.repository.task.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

    public void create(@Valid TaskRequestDto taskRequestDto) {
        TaskEntity newTask = TaskMapper.INSTANCE.mapToTaskEntity(taskRequestDto);
        newTask.setStatus(Status.TODO);
        ProjectEntity project = this.projectRepository.findById(taskRequestDto.projectId())
                .orElseThrow(() -> new RuntimeException("Não existe projeto com ID: " + taskRequestDto.projectId()));
        newTask.setProjectId(project);
        this.taskRepository.save(newTask);
    }
}
