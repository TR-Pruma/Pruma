package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ComunicacaoAuxRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoAuxResponseDTO;
import com.br.pruma.application.mapper.ComunicacaoAuxMapper;
import com.br.pruma.application.service.ComunicacaoAuxService;
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
@RequestMapping("/pruma/v1/comunicacoes-aux")
@Tag(name = "Comunicações Auxiliares", description = "API para gerenciamento de comunicações auxiliares")
public class ComunicacaoAuxController {

    private final ComunicacaoAuxService comunicacaoAuxService;
    private final ComunicacaoAuxMapper comunicacaoAuxMapper;

    @PostMapping
    @Operation(summary = "Criar uma nova comunicação auxiliar")
    public ResponseEntity<ComunicacaoAuxResponseDTO> criar(
            @Valid @RequestBody ComunicacaoAuxRequestDTO requestDTO) {
        var entity = comunicacaoAuxService.criar(requestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(entity.id()).toUri();
        return ResponseEntity.created(location).body(entity);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma comunicação auxiliar por ID")
    public ResponseEntity<ComunicacaoAuxResponseDTO> buscarPorId(
            @Parameter(description = "ID da comunicação auxiliar") @PathVariable Integer id) {
        return ResponseEntity.ok(comunicacaoAuxService.buscarPorId(id));
    }

    @GetMapping("/projeto/{projetoId}")
    @Operation(summary = "Listar comunicações auxiliares por projeto")
    public ResponseEntity<Page<ComunicacaoAuxResponseDTO>> listarPorProjeto(
            @Parameter(description = "ID do projeto") @PathVariable Integer projetoId,
            @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(comunicacaoAuxService.listarPorProjeto(projetoId, pageable));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar comunicações auxiliares por cliente")
    public ResponseEntity<List<ComunicacaoAuxResponseDTO>> listarPorCliente(
            @Parameter(description = "ID do cliente") @PathVariable Integer clienteId) {
        return ResponseEntity.ok(comunicacaoAuxService.listarPorCliente(clienteId));
    }

    @GetMapping("/comunicacao/{comunicacaoId}")
    @Operation(summary = "Buscar comunicação auxiliar por ID da comunicação")
    public ResponseEntity<ComunicacaoAuxResponseDTO> buscarPorComunicacaoId(
            @Parameter(description = "ID da comunicação") @PathVariable Integer comunicacaoId) {
        return ResponseEntity.ok(comunicacaoAuxService.buscarPorComunicacaoId(comunicacaoId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma comunicação auxiliar")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da comunicação auxiliar") @PathVariable Integer id) {
        comunicacaoAuxService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
