package dev.matheushbmelo.gestao_api.controller.project.impl;

import dev.matheushbmelo.gestao_api.controller.project.ProjectController;
import dev.matheushbmelo.gestao_api.controller.project.dto.ProjectRequestDto;
import dev.matheushbmelo.gestao_api.controller.project.dto.ProjectResponseDto;
import dev.matheushbmelo.gestao_api.service.project.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/projects")
public class ProjectControllerImpl implements ProjectController {
    private final ProjectService projectService;

    public ProjectControllerImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<Void> createNewProject(@RequestBody @Valid ProjectRequestDto projectRequestDto) {
        this.projectService.create(projectRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> findAllProjects() {
        return ResponseEntity.status(HttpStatus.OK).body(this.projectService.findAll());
    }
}
