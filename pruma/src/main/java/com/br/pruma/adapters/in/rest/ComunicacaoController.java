package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ComunicacaoRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoResponseDTO;
import com.br.pruma.application.service.ComunicacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pruma/v1/comunicacoes")
@Tag(name = "Comunicações", description = "API para gerenciamento de comunicações")
public class ComunicacaoController {

    private final ComunicacaoService comunicacaoService;

    @PostMapping
    @Operation(summary = "Criar uma nova comunicação")
    public ResponseEntity<ComunicacaoResponseDTO> criar(
            @Valid @RequestBody ComunicacaoRequestDTO requestDTO) {
        ComunicacaoResponseDTO salvo = comunicacaoService.criar(requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.projetoId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma comunicação por ID")
    public ResponseEntity<ComunicacaoResponseDTO> buscarPorId(
            @Parameter(description = "ID da comunicação")
            @PathVariable Integer id) {
        return ResponseEntity.ok(comunicacaoService.buscarPorId(id));
    }

    @GetMapping("/projeto/{projetoId}")
    @Operation(summary = "Listar comunicações por projeto")
    public ResponseEntity<Page<ComunicacaoResponseDTO>> listarPorProjeto(
            @Parameter(description = "ID do projeto") @PathVariable Integer projetoId,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(comunicacaoService.listarPorProjeto(projetoId, pageable));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar comunicações por cliente")
    public ResponseEntity<List<ComunicacaoResponseDTO>> listarPorCliente(
            @Parameter(description = "ID do cliente") @PathVariable Integer clienteId) {
        return ResponseEntity.ok(comunicacaoService.listarPorCliente(clienteId));
    }

    @GetMapping("/projeto/{projetoId}/cliente/{clienteId}")
    @Operation(summary = "Listar comunicações por projeto e cliente")
    public ResponseEntity<Page<ComunicacaoResponseDTO>> listarPorProjetoECliente(
            @Parameter(description = "ID do projeto") @PathVariable Integer projetoId,
            @Parameter(description = "ID do cliente") @PathVariable Integer clienteId,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(comunicacaoService.listarPorProjetoECliente(projetoId, clienteId, pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma comunicação")
    public ResponseEntity<ComunicacaoResponseDTO> atualizar(
            @Parameter(description = "ID da comunicação") @PathVariable Integer id,
            @Valid @RequestBody ComunicacaoRequestDTO requestDTO) {
        return ResponseEntity.ok(comunicacaoService.atualizar(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma comunicação")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da comunicação") @PathVariable Integer id) {
        comunicacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
