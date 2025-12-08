package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ComunicacaoRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoResponseDTO;
import com.br.pruma.application.mapper.ComunicacaoMapper;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.ComunicacaoService;
import com.br.pruma.core.repository.ProjetoRepository;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comunicacoes")
@Tag(name = "Comunicações", description = "API para gerenciamento de comunicações")
public class ComunicacaoController {

    private final ComunicacaoService comunicacaoService;
    private final ProjetoRepository projetoRepository;
    private final ClienteRepository clienteRepository;
    private final ComunicacaoMapper comunicacaoMapper;

    @PostMapping
    @Operation(summary = "Criar uma nova comunicação")
    public ResponseEntity<ComunicacaoResponseDTO> criar(
            @Valid @RequestBody ComunicacaoRequestDTO requestDTO) {
        Projeto projeto = projetoRepository.findById(requestDTO.getProjetoId())
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        Cliente cliente = clienteRepository.findById(requestDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        var entity = comunicacaoMapper.toEntity(requestDTO, projeto, cliente);
        var savedEntity = comunicacaoService.criar(entity);
        return ResponseEntity.ok(comunicacaoMapper.toDTO(savedEntity));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma comunicação por ID")
    public ResponseEntity<ComunicacaoResponseDTO> buscarPorId(
            @Parameter(description = "ID da comunicação")
            @PathVariable Integer id) {
        var entity = comunicacaoService.buscarPorId(id);
        return ResponseEntity.ok(comunicacaoMapper.toDTO(entity));
    }

    @GetMapping("/projeto/{projetoId}")
    @Operation(summary = "Listar comunicações por projeto")
    public ResponseEntity<Page<ComunicacaoResponseDTO>> listarPorProjeto(
            @Parameter(description = "ID do projeto")
            @PathVariable Integer projetoId,
            @ParameterObject Pageable pageable) {
        var page = comunicacaoService.listarPorProjeto(projetoId, pageable)
                .map(comunicacaoMapper::toDTO);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar comunicações por cliente")
    public ResponseEntity<List<ComunicacaoResponseDTO>> listarPorCliente(
            @Parameter(description = "ID do cliente")
            @PathVariable Integer clienteId) {
        var list = comunicacaoService.listarPorCliente(clienteId)
                .stream()
                .map(comunicacaoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/projeto/{projetoId}/cliente/{clienteId}")
    @Operation(summary = "Listar comunicações por projeto e cliente")
    public ResponseEntity<Page<ComunicacaoResponseDTO>> listarPorProjetoECliente(
            @Parameter(description = "ID do projeto")
            @PathVariable Integer projetoId,
            @Parameter(description = "ID do cliente")
            @PathVariable Integer clienteId,
            @ParameterObject Pageable pageable) {
        var page = comunicacaoService.listarPorProjetoECliente(projetoId, clienteId, pageable)
                .map(comunicacaoMapper::toDTO);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma comunicação")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da comunicação")
            @PathVariable Integer id) {
        comunicacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
