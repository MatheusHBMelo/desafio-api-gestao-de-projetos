package dev.matheushbmelo.gestao_api.service.project;

import dev.matheushbmelo.gestao_api.controller.project.dto.ProjectRequestDto;
import dev.matheushbmelo.gestao_api.controller.project.dto.ProjectResponseDto;
import dev.matheushbmelo.gestao_api.entity.project.ProjectEntity;
import dev.matheushbmelo.gestao_api.mapper.project.ProjectMapper;
import dev.matheushbmelo.gestao_api.repository.project.ProjectRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    private ProjectRequestDto projectRequestDto;
    private ProjectEntity project;

    @BeforeEach
    void setUp() {
        projectRequestDto = new ProjectRequestDto("Projeto 1", "Projeto de teste", LocalDate.now());
        project = ProjectEntity.builder().name(projectRequestDto.name()).description(projectRequestDto.description()).startDate(LocalDate.now()).endDate(projectRequestDto.endDate()).build();
    }

    @Test
    void criaProjetoComSucesso() {
        when(this.projectMapper.mapToProject(projectRequestDto)).thenReturn(project);
        when(this.projectRepository.save(any(ProjectEntity.class))).thenReturn(project);

        this.projectService.create(projectRequestDto);

        assertNotNull(project.getStartDate(), "A data de início não pode ser nula");
        assertEquals(LocalDate.now(), project.getStartDate(), "A data de início deve ser a data atual");
        assertEquals(project.getName(), projectRequestDto.name());
        assertEquals(project.getDescription(), projectRequestDto.description());

        verify(this.projectMapper, Mockito.times(1)).mapToProject(projectRequestDto);
        verify(this.projectRepository, Mockito.times(1)).save(project);
    }

    @Test
    void deveRetornarExcecaoSeMapperRetornarNull() {
        when(this.projectMapper.mapToProject(projectRequestDto)).thenReturn(null);

        NullPointerException ex = assertThrows(NullPointerException.class, () -> this.projectService.create(projectRequestDto));

        assertNotNull(ex);

        verifyNoInteractions(this.projectRepository);
    }

    @Test
    void deveSobrescreverStartDateMesmoQueDtoTenhaValor() {
        ProjectRequestDto dto = new ProjectRequestDto("Projeto", "Teste", LocalDate.of(2000, 1, 1));
        ProjectEntity mappedEntity = ProjectEntity.builder()
                .name(dto.name())
                .description(dto.description())
                .startDate(dto.endDate())
                .build();

        when(projectMapper.mapToProject(dto)).thenReturn(mappedEntity);

        this.projectService.create(dto);

        assertEquals(LocalDate.now(), mappedEntity.getStartDate());
    }

    @Test
    void deveRetornarListaDeProjetosCorretamente() {
        ProjectEntity project2 = ProjectEntity.builder().id(1L).name("Projeto 2").description("Teste 2").startDate(LocalDate.now()).build();

        List<ProjectEntity> projects = List.of(project, project2);
        List<ProjectResponseDto> projectsDtos = List.of(new ProjectResponseDto(1L, "Projeto 1", "Projeto teste", LocalDate.now()),
                                                        new ProjectResponseDto(2L, "Projeto 2", "Teste 2", LocalDate.now()));

        when(this.projectRepository.findAll()).thenReturn(projects);
        when(this.projectMapper.mapToProjectResponseDtoList(projects)).thenReturn(projectsDtos);

        List<ProjectResponseDto> response = this.projectService.findAll();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Projeto 1", response.get(0).name());
        assertEquals("Projeto 2", response.get(1).name());


        verify(this.projectRepository, times(1)).findAll();
        verify(this.projectMapper, times(1)).mapToProjectResponseDtoList(projects);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoExistiremProjetos() {
        when(projectRepository.findAll()).thenReturn(Collections.emptyList());
        when(projectMapper.mapToProjectResponseDtoList(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<ProjectResponseDto> resultado = projectService.findAll();

        assertNotNull(resultado, "O retorno não deve ser nulo");
        assertTrue(resultado.isEmpty(), "A lista deve estar vazia");

        verify(projectRepository, times(1)).findAll();
        verify(projectMapper, times(1)).mapToProjectResponseDtoList(Collections.emptyList());
    }

    @Test
    void devePropagarExcecaoDoRepository() {
        when(projectRepository.findAll()).thenThrow(new RuntimeException("Erro no banco"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> projectService.findAll());
        assertEquals("Erro no banco", ex.getMessage());

        verify(projectRepository, times(1)).findAll();
        verify(projectMapper, never()).mapToProjectResponseDtoList(any());
    }
}