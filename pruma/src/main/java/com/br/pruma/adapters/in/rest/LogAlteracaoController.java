package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.LogAlteracaoRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoResponseDTO;
import com.br.pruma.application.service.LogAlteracaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pruma/v1/log-alteracoes")
@Tag(name = "LogAlteracao", description = "Gerencia registros de log de alterações")
@RequiredArgsConstructor
public class LogAlteracaoController {

    private final LogAlteracaoService service;

    @Operation(summary = "Cria um novo registro de log de alteração")
    @PostMapping
    public ResponseEntity<LogAlteracaoResponseDTO> create(
            @Valid @RequestBody LogAlteracaoRequestDTO request) {
        LogAlteracaoResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os registros de log de alteração (paginado)")
    @GetMapping
    public ResponseEntity<Page<LogAlteracaoResponseDTO>> listAll(
            @PageableDefault(size = 20, sort = "dataHora") Pageable pageable) {
        return ResponseEntity.ok(service.listAll(pageable));
    }

    @Operation(summary = "Busca um registro de log de alteração pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<LogAlteracaoResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista logs de alteração filtrados por projeto (paginado)")
    @GetMapping("/projetos/{projetoId}")
    public ResponseEntity<Page<LogAlteracaoResponseDTO>> listByProjeto(
            @PathVariable Integer projetoId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.listByProjeto(projetoId, pageable));
    }

    @Operation(summary = "Lista logs de alteração filtrados por cliente (CPF) (paginado)")
    @GetMapping("/clientes/{clienteCpf}")
    public ResponseEntity<Page<LogAlteracaoResponseDTO>> listByCliente(
            @PathVariable String clienteCpf,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.listByCliente(clienteCpf, pageable));
    }

    @Operation(summary = "Lista logs de alteração filtrados por tipo de usuário (paginado)")
    @GetMapping("/tipo-usuarios/{tipoUsuarioId}")
    public ResponseEntity<Page<LogAlteracaoResponseDTO>> listByTipoUsuario(
            @PathVariable Integer tipoUsuarioId,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.listByTipoUsuario(tipoUsuarioId, pageable));
    }

    @Operation(summary = "Remove um registro de log de alteração")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
