package dev.matheushbmelo.gestao_api.mapper.task;

import dev.matheushbmelo.gestao_api.controller.task.dto.TaskRequestDto;
import dev.matheushbmelo.gestao_api.controller.task.dto.TaskResponseDto;
import dev.matheushbmelo.gestao_api.entity.task.TaskEntity;
import dev.matheushbmelo.gestao_api.mapper.project.ProjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProjectMapper.class)
public interface TaskMapper {
    @Mapping(target = "projectId.id", source = "projectId")
    TaskEntity mapToTaskEntity(TaskRequestDto taskRequestDto);

    List<TaskResponseDto> mapToTaskResponseDtoList(List<TaskEntity> tasks);
}
