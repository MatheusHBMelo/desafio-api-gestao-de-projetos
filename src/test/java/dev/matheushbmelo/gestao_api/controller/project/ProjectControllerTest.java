package dev.matheushbmelo.gestao_api.controller.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.matheushbmelo.gestao_api.controller.project.dto.ProjectRequestDto;
import dev.matheushbmelo.gestao_api.controller.project.dto.ProjectResponseDto;
import dev.matheushbmelo.gestao_api.controller.project.impl.ProjectControllerImpl;
import dev.matheushbmelo.gestao_api.service.project.ProjectService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProjectControllerImpl.class)
@AutoConfigureMockMvc(addFilters = false)
class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveRetornarStatus201AoCriarNovoProjeto() throws Exception {
        ProjectRequestDto dto = new ProjectRequestDto("Projeto 1", "Projeto 1", LocalDate.of(2025, 12, 25));

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(projectService).create(any(ProjectRequestDto.class));

        verify(projectService).create(any(ProjectRequestDto.class));
    }

    @Test
    void deveRetornar400QuandoDadosForemInvalidos() throws Exception {
        ProjectRequestDto dto = new ProjectRequestDto("", "Projeto 1", LocalDate.of(2025, 12, 25));

        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(projectService, never()).create(any());
    }

    @Test
    void deveRetornarListaDeProjetosComStatus200() throws Exception {
        List<ProjectResponseDto> projetos = List.of(
                new ProjectResponseDto(1L, "Projeto 1", "Descrição 1", LocalDate.of(2025, 12, 25)),
                new ProjectResponseDto(2L, "Projeto 2", "Descrição 2", LocalDate.of(2025, 12, 31))
        );

        when(projectService.findAll()).thenReturn(projetos);

        mockMvc.perform(get("/projects")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(projetos)));

        verify(projectService).findAll();
    }
}