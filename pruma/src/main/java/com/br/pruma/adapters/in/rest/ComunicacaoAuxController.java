package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ComunicacaoAuxRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoAuxResponseDTO;
import com.br.pruma.application.mapper.ComunicacaoAuxMapper;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Comunicacao;
import com.br.pruma.core.domain.ComunicacaoAux;
import com.br.pruma.core.repository.ComunicacaoAuxService;
import com.br.pruma.core.repository.ComunicacaoRepository;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comunicacoes-aux")
@Tag(name = "Comunicações Auxiliares", description = "API para gerenciamento de comunicações auxiliares")
public class ComunicacaoAuxController {

    private final ComunicacaoAuxService comunicacaoAuxService;
    private final ComunicacaoRepository comunicacaoRepository;
    private final ComunicacaoAuxMapper comunicacaoAuxMapper;

    @PostMapping
    @Operation(summary = "Criar uma nova comunicação auxiliar")
    public ResponseEntity<ComunicacaoAuxResponseDTO> criar(
            @Valid @RequestBody ComunicacaoAuxRequestDTO requestDTO) {
        Comunicacao comunicacao = comunicacaoRepository.findByIdAndAtivoTrue(requestDTO.getComunicacaoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Comunicação não encontrada"));

        var entity = comunicacaoAuxMapper.toEntity(requestDTO, comunicacao);
        var savedEntity = comunicacaoAuxService.criar(entity);
        return ResponseEntity.ok(comunicacaoAuxMapper.toDTO(savedEntity));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma comunicação auxiliar por ID")
    public ResponseEntity<ComunicacaoAuxResponseDTO> buscarPorId(
            @Parameter(description = "ID da comunicação auxiliar")
            @PathVariable Integer id) {
        var entity = comunicacaoAuxService.buscarPorId(id);
        return ResponseEntity.ok(comunicacaoAuxMapper.toDTO(entity));
    }

    @GetMapping("/projeto/{projetoId}")
    @Operation(summary = "Listar comunicações auxiliares por projeto")
    public ResponseEntity<Page<ComunicacaoAuxResponseDTO>> listarPorProjeto(
            @Parameter(description = "ID do projeto")
            @PathVariable Integer projetoId,
            @ParameterObject Pageable pageable) {
        var page = comunicacaoAuxService.listarPorProjeto(projetoId, pageable)
                .map(comunicacaoAuxMapper::toDTO);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar comunicações auxiliares por cliente")
    public ResponseEntity<List<ComunicacaoAuxResponseDTO>> listarPorCliente(
            @Parameter(description = "ID do cliente")
            @PathVariable Integer clienteId) {
        var list = comunicacaoAuxService.listarPorCliente(clienteId)
                .stream()
                .map(comunicacaoAuxMapper::toDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/comunicacao/{comunicacaoId}")
    @Operation(summary = "Buscar comunicação auxiliar por ID da comunicação")
    public ResponseEntity<ComunicacaoAuxResponseDTO> buscarPorComunicacaoId(
            @Parameter(description = "ID da comunicação")
            @PathVariable Integer comunicacaoId) {
        var entity = comunicacaoAuxService.buscarPorComunicacaoId(comunicacaoId);
        return ResponseEntity.ok(comunicacaoAuxMapper.toDTO((ComunicacaoAux) entity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma comunicação auxiliar")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da comunicação auxiliar")
            @PathVariable Integer id) {
        comunicacaoAuxService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
