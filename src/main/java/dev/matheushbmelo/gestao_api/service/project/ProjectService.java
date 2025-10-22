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
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public void create(@Valid ProjectRequestDto projectRequestDto) {
        ProjectEntity newProject = this.projectMapper.mapToProject(projectRequestDto);
        newProject.setStartDate(LocalDate.now());
        this.projectRepository.save(newProject);
    }

    public List<ProjectResponseDto> findAll() {
        return this.projectMapper.mapToProjectResponseDtoList(this.projectRepository.findAll());
    }
}
