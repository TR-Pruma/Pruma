package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.LogAlteracaoRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoResponseDTO;
import com.br.pruma.application.service.LogAlteracaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pruma/v1/log-alteracoes")
@Tag(name = "LogAlteracao", description = "Gerencia registros de log de alterações")
@RequiredArgsConstructor

public class LogAlteracaoController {

    private final LogAlteracaoService service;

    @Operation(summary = "Cria um novo registro de log de alteração")
    @PostMapping
    public ResponseEntity<LogAlteracaoResponseDTO> create(
            @Valid @RequestBody LogAlteracaoRequestDTO request
    ) {
        LogAlteracaoResponseDTO response = service.create(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os registros de log de alteração")
    @GetMapping
    public ResponseEntity<List<LogAlteracaoResponseDTO>> listAll() {
        List<LogAlteracaoResponseDTO> logs = service.listAll();
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Busca um registro de log de alteração pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<LogAlteracaoResponseDTO> getById(
            @PathVariable Integer id
    ) {
        LogAlteracaoResponseDTO response = service.getById(id);
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Lista logs de alteração filtrados por projeto")
    @GetMapping("/projetos/{projetoId}")
    public ResponseEntity<List<LogAlteracaoResponseDTO>> listByProjeto(
            @PathVariable Integer projetoId
    ) {
        List<LogAlteracaoResponseDTO> logs = service.listByProjeto(projetoId);
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Lista logs de alteração filtrados por cliente (CPF)")
    @GetMapping("/clientes/{clienteCpf}")
    public ResponseEntity<List<LogAlteracaoResponseDTO>> listByCliente(
            @PathVariable String clienteCpf
    ) {
        List<LogAlteracaoResponseDTO> logs = service.listByCliente(clienteCpf);
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Lista logs de alteração filtrados por tipo de usuário")
    @GetMapping("/tipo-usuarios/{tipoUsuarioId}")
    public ResponseEntity<List<LogAlteracaoResponseDTO>> listByTipoUsuario(
            @PathVariable Integer tipoUsuarioId
    ) {
        List<LogAlteracaoResponseDTO> logs = service.listByTipoUsuario(tipoUsuarioId);
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Remove um registro de log de alteração")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}