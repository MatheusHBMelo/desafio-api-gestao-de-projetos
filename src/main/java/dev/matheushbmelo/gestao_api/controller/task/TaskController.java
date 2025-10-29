package dev.matheushbmelo.gestao_api.controller.task;

import dev.matheushbmelo.gestao_api.controller.task.dto.StatusDto;
import dev.matheushbmelo.gestao_api.controller.task.dto.TaskRequestDto;
import dev.matheushbmelo.gestao_api.controller.task.dto.TaskResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Task Controller", description = "Gerencia as tarefas dos projetos")
@RequestMapping("/tasks")
public interface TaskController {
    @Operation(
            summary = "Criar nova tarefa",
            description = "Cria uma nova tarefa vinculada a um projeto existente",
            tags = {"task", "post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados"),
            @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    @PostMapping(consumes = "application/json")
    ResponseEntity<Void> createTask(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da nova tarefa",
            required = true,
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "name": "Criar endpoint de login",
                                      "description": "Implementar autenticação JWT e controle de roles",
                                      "priority": "HIGH",
                                      "projectId": 1
                                    }
                                    """
                    )
            )) @RequestBody @Valid TaskRequestDto taskRequestDto);

    @Operation(summary = "Buscar tarefas por filtros", description = "Filtra tarefas por status, prioridade e ID do projeto", tags = {"task", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefas retornadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TaskResponseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Parâmetro inválido")
    })
    @GetMapping(produces = "application/json")
    ResponseEntity<List<TaskResponseDto>> findByParam(@Parameter(description = "Status da tarefa", examples = {
                                                              @ExampleObject(name = "TODO", value = "TODO"),
                                                              @ExampleObject(name = "DOING", value = "DOING"),
                                                              @ExampleObject(name = "DONE", value = "DONE")
                                                      }) @RequestParam(required = true) String status,
                                                      @Parameter(description = "Prioridade da tarefa", examples = {
                                                              @ExampleObject(name = "LOW", value = "LOW"),
                                                              @ExampleObject(name = "MEDIUM", value = "MEDIUM"),
                                                              @ExampleObject(name = "HIGH", value = "HIGH")
                                                      }) @RequestParam(required = true) String priority,
                                                      @Parameter(description = "ID do projeto ao qual a tarefa pertence", required = true) @RequestParam(required = true) Long projectId);

    @Operation(summary = "Atualizar status da tarefa", description = "Atualiza o status da tarefa (TODO, DOING, DONE)", tags = {"task", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "400", description = "Status inválido")
    })
    @PutMapping(path = "/{id}/status", consumes = "application/json")
    ResponseEntity<Void> updateTask(@Parameter(description = "ID da tarefa a ser atualizada") @PathVariable(value = "id") Long id,
                                    @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                            description = "Novo status da tarefa",
                                            required = true,
                                            content = @Content(mediaType = "application/json",
                                                    examples = @ExampleObject(value = "{ \"status\": \"DONE\" }")
                                            )
                                    ) @RequestBody StatusDto statusDto);

    @Operation(summary = "Deletar tarefa", description = "Remove uma tarefa do banco de dados", tags = {"task", "delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @DeleteMapping(path = "/{id}")
    ResponseEntity<Void> deleteTask(@Parameter(description = "ID da tarefa") @PathVariable(value = "id") Long id);
}
