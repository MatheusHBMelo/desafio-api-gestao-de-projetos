package dev.matheushbmelo.gestao_api.mapper.project;

import dev.matheushbmelo.gestao_api.controller.project.dto.ProjectRequestDto;
import dev.matheushbmelo.gestao_api.entity.project.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectEntity mapToProject(ProjectRequestDto projectRequestDto);
}
