package dev.matheushbmelo.gestao_api.controller.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.matheushbmelo.gestao_api.controller.task.dto.StatusDto;
import dev.matheushbmelo.gestao_api.controller.task.dto.TaskRequestDto;
import dev.matheushbmelo.gestao_api.controller.task.dto.TaskResponseDto;
import dev.matheushbmelo.gestao_api.entity.task.Priority;
import dev.matheushbmelo.gestao_api.entity.task.Status;
import dev.matheushbmelo.gestao_api.service.exceptions.StatusNotFoundException;
import dev.matheushbmelo.gestao_api.service.exceptions.TaskNotFoundException;
import dev.matheushbmelo.gestao_api.service.task.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveRetornarStatusCode201SeTarefaCriadaCorretamente() throws Exception {
        TaskRequestDto taskRequestDto = new TaskRequestDto("Task 1", "Task 1", Priority.LOW, LocalDate.now(), 1L);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestDto)))
                .andExpect(status().isCreated());

        verify(this.taskService).create(any(TaskRequestDto.class));
    }

    @Test
    void deveRetornarStatuscode400SeEntradasForemNulas() throws Exception {
        TaskRequestDto taskRequestDto = new TaskRequestDto("", "Task 1", Priority.LOW, LocalDate.now(), null);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequestDto)))
                .andExpect(status().isBadRequest());

        verify(this.taskService, never()).create(any());
    }

    @Test
    void deveRetornarListaDeTasksFiltradasComSucesso() throws Exception {
        String status = "OPEN";
        String priority = "HIGH";
        Long projectId = 1L;

        List<TaskResponseDto> tasks = List.of(new TaskResponseDto(1L, "Task1", "Task1", Status.TODO, Priority.LOW, LocalDate.now(), 1L));

        when(this.taskService.findByFilter(anyString(), anyString(), anyLong())).thenReturn(tasks);

        mockMvc.perform(get("/tasks")
                        .param("status", status)
                        .param("priority", priority)
                        .param("projectId", String.valueOf(projectId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(this.taskService).findByFilter(status, priority, projectId);
    }

    @Test
    void deveRetornar400QuandoFaltarParametroStatus() throws Exception {
        mockMvc.perform(get("/tasks")
                        .param("priority", "HIGH")
                        .param("projectId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(taskService, never()).findByFilter(any(), any(), any());
    }

    @Test
    void deveRetornar400QuandoFaltarParametroPriority() throws Exception {
        mockMvc.perform(get("/tasks")
                        .param("status", "TODO")
                        .param("projectId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(taskService, never()).findByFilter(any(), any(), any());
    }

    @Test
    void deveRetornar400QuandoFaltarParametroProjectId() throws Exception {
        mockMvc.perform(get("/tasks")
                        .param("status", "TODO")
                        .param("priority", "HIGH")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(taskService, never()).findByFilter(any(), any(), any());
    }

    @Test
    void deveRetornar200QuandoAtualizarStatusComSucesso() throws Exception {
        StatusDto statusDto = new StatusDto("DONE");

        mockMvc.perform(put("/tasks/{id}/status", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusDto)))
                .andExpect(status().isOk());

        verify(taskService).updateStatus(eq(1L), any(StatusDto.class));
    }

    @Test
    void deveRetornar404QuandoTarefaNaoExistir() throws Exception {
        StatusDto statusDto = new StatusDto("DONE");

        doThrow(new TaskNotFoundException("Não existe tarefa com ID: 1"))
                .when(taskService).updateStatus(eq(1L), any(StatusDto.class));

        mockMvc.perform(put("/tasks/{id}/status", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusDto)))
                .andExpect(status().isNotFound());

        verify(taskService).updateStatus(eq(1L), any(StatusDto.class));
    }

    @Test
    void deveRetornar404QuandoStatusInvalido() throws Exception {
        StatusDto statusDto = new StatusDto("INVALIDO");

        doThrow(new StatusNotFoundException("Status inválido"))
                .when(taskService).updateStatus(eq(1L), any(StatusDto.class));

        mockMvc.perform(put("/tasks/{id}/status", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(statusDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveDeletarTaskComSucesso() throws Exception {
        Long id = 1L;

        doNothing().when(taskService).deleteTask(id);

        mockMvc.perform(delete("/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).deleteTask(id);
    }

    @Test
    void deveRetornar404QuandoTaskNaoForEncontrada() throws Exception {
        Long id = 99L;

        doThrow(new TaskNotFoundException("Task não encontrada"))
                .when(taskService).deleteTask(id);

        mockMvc.perform(delete("/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).deleteTask(id);
    }
}