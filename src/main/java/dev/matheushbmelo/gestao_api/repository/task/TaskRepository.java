package dev.matheushbmelo.gestao_api.repository.task;

import dev.matheushbmelo.gestao_api.entity.task.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
