package dev.matheushbmelo.gestao_api.mapper.task;

import dev.matheushbmelo.gestao_api.controller.task.dto.TaskRequestDto;
import dev.matheushbmelo.gestao_api.entity.task.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskEntity mapToTaskEntity(TaskRequestDto taskRequestDto);
}
