package dev.matheushbmelo.gestao_api.repository.task;

import dev.matheushbmelo.gestao_api.entity.task.Priority;
import dev.matheushbmelo.gestao_api.entity.task.Status;
import dev.matheushbmelo.gestao_api.entity.task.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("""
                SELECT t FROM TaskEntity t
                WHERE t.status = :status
                  AND t.priority = :priority
                  AND t.projectId.id = :projectId
            """)
    List<TaskEntity> findByFilters(@Param("status") Status status, @Param("priority") Priority priority, @Param("projectId") Long projectId);
}
