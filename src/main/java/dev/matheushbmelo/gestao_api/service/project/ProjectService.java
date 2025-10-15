package dev.matheushbmelo.gestao_api.service.project;

import dev.matheushbmelo.gestao_api.controller.project.dto.ProjectRequestDto;
import dev.matheushbmelo.gestao_api.controller.project.dto.ProjectResponseDto;
import dev.matheushbmelo.gestao_api.entity.project.ProjectEntity;
import dev.matheushbmelo.gestao_api.mapper.project.ProjectMapper;
import dev.matheushbmelo.gestao_api.repository.project.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void create(@Valid ProjectRequestDto projectRequestDto) {
        ProjectEntity newProject = ProjectMapper.INSTANCE.mapToProject(projectRequestDto);
        newProject.setStartDate(LocalDate.now());
        this.projectRepository.save(newProject);
    }

    public List<ProjectResponseDto> findAll() {
        return ProjectMapper.INSTANCE.mapToProjectResponseDtoList(this.projectRepository.findAll());
    }
}
