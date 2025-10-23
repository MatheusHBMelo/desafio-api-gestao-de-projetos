package dev.matheushbmelo.gestao_api.entity.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.matheushbmelo.gestao_api.entity.project.ProjectEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @JsonFormat(pattern = "dd/MM/yyyy ")
    private LocalDate dueDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity projectId;
}
