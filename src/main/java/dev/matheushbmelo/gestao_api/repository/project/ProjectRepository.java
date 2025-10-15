package dev.matheushbmelo.gestao_api.repository.project;

import dev.matheushbmelo.gestao_api.entity.project.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}
