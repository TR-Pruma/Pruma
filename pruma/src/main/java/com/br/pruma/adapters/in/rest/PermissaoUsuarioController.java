package com.br.pruma.adapters.in.rest;



import com.br.pruma.application.dto.request.PermissaoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.PermissaoUsuarioResponseDTO;
import com.br.pruma.application.service.PermissaoUsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pruma/v1/permissoes")
@RequiredArgsConstructor
@Tag(name = "Permissões de Usuário", description = "API para gerenciamento de permissões de usuário")
public class PermissaoUsuarioController {

    private final PermissaoUsuarioService service;

    @Operation(summary = "Cria uma nova permissão de usuário", description = "Endpoint para associar uma permissão a um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Permissão criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<PermissaoUsuarioResponseDTO> criar(@Valid @RequestBody PermissaoUsuarioRequestDTO dto) {
        PermissaoUsuarioResponseDTO response = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "Busca uma permissão por ID", description = "Retorna os detalhes de uma permissão específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissão encontrada"),
            @ApiResponse(responseCode = "404", description = "Permissão não encontrada para o ID informado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PermissaoUsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Atualiza uma permissão existente", description = "Atualiza os dados de uma permissão com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissão atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Permissão não encontrada para o ID informado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PermissaoUsuarioResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody PermissaoUsuarioRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Deleta uma permissão", description = "Remove uma permissão do sistema com base no seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Permissão deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Permissão não encontrada para o ID informado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Busca permissões por CPF do cliente", description = "Retorna uma lista de permissões associadas a um cliente específico pelo CPF.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissões encontradas"),
    })
    @GetMapping("/cliente/{clienteCpf}")
    public ResponseEntity<List<PermissaoUsuarioResponseDTO>> buscarPorCliente(@PathVariable String clienteCpf) {
        return ResponseEntity.ok(service.buscarPorCliente(clienteCpf));
    }

    @Operation(summary = "Busca permissões por ID do tipo de usuário", description = "Retorna uma lista de permissões associadas a um tipo de usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permissões encontradas"),
    })
    @GetMapping("/tipo/{tipoUsuarioId}")
    public ResponseEntity<List<PermissaoUsuarioResponseDTO>> buscarPorTipoUsuario(@PathVariable Integer tipoUsuarioId) {
        return ResponseEntity.ok(service.buscarPorTipoUsuario(tipoUsuarioId));
    }
}
