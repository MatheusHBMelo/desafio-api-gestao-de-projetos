package dev.matheushbmelo.gestao_api.controller.project;

import dev.matheushbmelo.gestao_api.controller.project.dto.ProjectRequestDto;
import dev.matheushbmelo.gestao_api.controller.project.dto.ProjectResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Tag(name = "Projects", description = "Endpoints relacionados a projetos")
@RequestMapping("/projects")
public interface ProjectController {
    @Operation(
            summary = "Cria um novo projeto",
            description = "Cria e salva um novo projeto no banco de dados."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Projeto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\": \"O campo 'name' é obrigatório.\"}"))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    ResponseEntity<Void> createNewProject(@RequestBody @Valid @Schema(description = "Dados necessários para criação de um projeto") ProjectRequestDto projectRequestDto);

    @Operation(
            summary = "Lista todos os projetos",
            description = "Retorna uma lista com todos os projetos cadastrados no sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjectResponseDto.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum projeto encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    ResponseEntity<List<ProjectResponseDto>> findAllProjects();
}
