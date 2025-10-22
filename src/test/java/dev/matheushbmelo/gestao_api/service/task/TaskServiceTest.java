package dev.matheushbmelo.gestao_api.service.task;

import dev.matheushbmelo.gestao_api.controller.task.dto.StatusDto;
import dev.matheushbmelo.gestao_api.controller.task.dto.TaskRequestDto;
import dev.matheushbmelo.gestao_api.controller.task.dto.TaskResponseDto;
import dev.matheushbmelo.gestao_api.entity.project.ProjectEntity;
import dev.matheushbmelo.gestao_api.entity.task.Priority;
import dev.matheushbmelo.gestao_api.entity.task.Status;
import dev.matheushbmelo.gestao_api.entity.task.TaskEntity;
import dev.matheushbmelo.gestao_api.mapper.task.TaskMapper;
import dev.matheushbmelo.gestao_api.repository.project.ProjectRepository;
import dev.matheushbmelo.gestao_api.repository.task.TaskRepository;
import dev.matheushbmelo.gestao_api.service.exceptions.ProjectNotFoundException;
import dev.matheushbmelo.gestao_api.service.exceptions.StatusNotFoundException;
import dev.matheushbmelo.gestao_api.service.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private ProjectRepository projectRepository;

    TaskRequestDto taskRequestDto;
    TaskEntity taskEntity;
    ProjectEntity project;
    Optional<ProjectEntity> optionalProject;
    Optional<TaskEntity> optionalTask;
    List<TaskEntity> tasks;
    List<TaskResponseDto> tasksDto;

    @BeforeEach
    void setUp() {
        taskRequestDto = new TaskRequestDto("Tarefa 1", "Tarefa 1", Priority.LOW, LocalDate.now(), 1L);
        taskEntity = TaskEntity.builder().id(1L).title("Tarefa 1").description("Tarefa 1").dueDate(LocalDate.now()).status(Status.TODO).priority(Priority.LOW).build();
        project = ProjectEntity.builder().id(1L).name("Projeto 1").description("Projeto 1").build();
        optionalProject = Optional.of(project);
        optionalTask = Optional.of(taskEntity);
        tasks = List.of(taskEntity);
        tasksDto = List.of(new TaskResponseDto(1L, "Task 1", "Task 1", Status.TODO, Priority.LOW, LocalDate.now(), 1L));
    }

    @Test
    void deveCriarTarefaComSucesso() {
        when(this.taskMapper.mapToTaskEntity(taskRequestDto)).thenReturn(taskEntity);
        when(this.projectRepository.findById(anyLong())).thenReturn(optionalProject);
        when(this.taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

        this.taskService.create(taskRequestDto);

        assertEquals(taskEntity.getTitle(), taskRequestDto.title());
        assertEquals(taskEntity.getDescription(), taskRequestDto.description());

        verify(this.taskMapper, times(1)).mapToTaskEntity(taskRequestDto);
        verify(this.projectRepository, times(1)).findById(1L);
        verify(this.taskRepository, times(1)).save(taskEntity);
    }

    @Test
    void deveRetornarExcessaoSeMapperDevolverNulo() {
        when(this.taskMapper.mapToTaskEntity(taskRequestDto)).thenReturn(null);

        NullPointerException ex = assertThrows(NullPointerException.class, () -> this.taskService.create(taskRequestDto));

        assertNotNull(ex);

        verifyNoInteractions(this.projectRepository);
        verifyNoInteractions(this.taskRepository);
    }

    @Test
    void deveRetornarExcessaoSeNaoTiverProjetoCadastradoComIdInformado() {
        when(this.taskMapper.mapToTaskEntity(taskRequestDto)).thenReturn(taskEntity);
        when(this.projectRepository.findById(anyLong())).thenThrow(new ProjectNotFoundException("Não existe projeto com ID:" + taskRequestDto.projectId()));

        ProjectNotFoundException ex = assertThrows(ProjectNotFoundException.class, () -> this.taskService.create(taskRequestDto));

        assertNotNull(ex);
        assertEquals("Não existe projeto com ID:1", ex.getMessage());

        verify(this.taskRepository, times(0)).save(taskEntity);
    }

    @Test
    void deveRetornarUmaListaDeTarefaDeAcordoComOFiltro() {
        String status = "TODO";
        String priority = "HIGH";
        Long projectId = 1L;

        when(this.taskRepository.findByFilters(any(Status.class), any(Priority.class), anyLong())).thenReturn(tasks);
        when(this.taskMapper.mapToTaskResponseDtoList(tasks)).thenReturn(tasksDto);

        List<TaskResponseDto> resultado = taskService.findByFilter(status, priority, projectId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(tasksDto, resultado);

        verify(taskRepository).findByFilters(Status.TODO, Priority.HIGH, projectId);
        verify(taskMapper).mapToTaskResponseDtoList(tasks);
    }

    @Test
    void deveLancarExcecaoQuandoStatusForInvalido() {
        String status = "INVALIDO";
        String priority = "HIGH";
        Long projectId = 1L;

        assertThrows(IllegalArgumentException.class, () -> {
            taskService.findByFilter(status, priority, projectId);
        });

        verifyNoInteractions(taskRepository);
        verifyNoInteractions(taskMapper);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremTarefasComOFiltro() {
        String status = "DONE";
        String priority = "LOW";
        Long projectId = 2L;

        when(taskRepository.findByFilters(Status.DONE, Priority.LOW, projectId))
                .thenReturn(Collections.emptyList());
        when(taskMapper.mapToTaskResponseDtoList(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        List<TaskResponseDto> resultado = taskService.findByFilter(status, priority, projectId);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(taskRepository).findByFilters(Status.DONE, Priority.LOW, projectId);
        verify(taskMapper).mapToTaskResponseDtoList(Collections.emptyList());
    }

    @Test
    void deveAtualizarStatusDaTarefaComSucesso() {
        StatusDto dto = new StatusDto("DONE");

        when(this.taskRepository.findById(anyLong())).thenReturn(optionalTask);

        this.taskService.updateStatus(1L, dto);

        assertEquals(Status.DONE, optionalTask.get().getStatus());

        verify(this.taskRepository).save(taskEntity);
    }

    @Test
    void deveRetornarExceptionSeNaoExistirTarefaComOIdInformado() {
        StatusDto dto = new StatusDto("DONE");
        when(this.taskRepository.findById(anyLong())).thenReturn(Optional.empty());

        TaskNotFoundException ex = assertThrows(TaskNotFoundException.class, () -> this.taskService.updateStatus(6L, dto));

        assertNotNull(ex);
        assertEquals("Não existe tarefa com ID: 6", ex.getMessage());

        verify(this.taskRepository).findById(6L);
        verify(this.taskRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoStatusParaAtualizarForInvalido() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(taskEntity));

        StatusDto dto = new StatusDto("INVALIDO");

        assertThrows(StatusNotFoundException.class, () -> taskService.updateStatus(1L, dto));

        verify(taskRepository, never()).save(any());
    }

    @Test
    void deveDeletarTarefaComSucesso() {
        Long id = 1L;
        when(taskRepository.existsById(id)).thenReturn(true);

        taskService.deleteTask(id);

        verify(taskRepository).existsById(id);
        verify(taskRepository).deleteById(id);
    }

    @Test
    void deveLancarExcecaoQuandoTarefaNaoExistir() {
        Long id = 1L;
        when(taskRepository.existsById(id)).thenReturn(false);

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(id));

        verify(taskRepository).existsById(id);
        verify(taskRepository, never()).deleteById(anyLong());
    }
}