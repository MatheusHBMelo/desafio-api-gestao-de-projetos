package dev.matheushbmelo.gestao_api.mapper.project;

import dev.matheushbmelo.gestao_api.controller.project.dto.ProjectRequestDto;
import dev.matheushbmelo.gestao_api.controller.project.dto.ProjectResponseDto;
import dev.matheushbmelo.gestao_api.entity.project.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectEntity mapToProject(ProjectRequestDto projectRequestDto);

    List<ProjectResponseDto> mapToProjectResponseDtoList(List<ProjectEntity> projects);

    default Long map(ProjectEntity projectEntity) {
        return projectEntity != null ? projectEntity.getId() : null;
    }
}
