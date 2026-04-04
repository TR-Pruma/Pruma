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

@Tag(name = "LogAlteracao", description = "Operações relacionadas a logs de alteração")
@RestController
@RequestMapping("/pruma/v1/logs-alteracao")
@RequiredArgsConstructor
public class LogAlteracaoController {

    private final LogAlteracaoService service;

    @Operation(summary = "Lista todos os logs")
    @GetMapping
    public ResponseEntity<List<LogAlteracaoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista logs por projeto")
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<LogAlteracaoResponseDTO>> listarPorProjeto(@PathVariable Integer projetoId) {
        return ResponseEntity.ok(service.listByProjeto(projetoId));
    }

    @Operation(summary = "Busca log por ID")
    @GetMapping("/{id}")
    public ResponseEntity<LogAlteracaoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria novo log")
    @PostMapping
    public ResponseEntity<LogAlteracaoResponseDTO> criar(@RequestBody @Valid LogAlteracaoRequestDTO dto) {
        LogAlteracaoResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.id()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza log por ID")
    @PutMapping("/{id}")
    public ResponseEntity<LogAlteracaoResponseDTO> atualizar(@PathVariable Integer id,
                                                             @RequestBody @Valid LogAlteracaoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta log por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
