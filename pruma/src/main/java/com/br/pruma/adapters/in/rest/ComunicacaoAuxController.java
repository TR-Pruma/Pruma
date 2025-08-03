package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ComunicacaoAuxRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoAuxResponseDTO;

import com.br.pruma.core.repository.ComunicacaoAuxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunicacoes-aux")
@RequiredArgsConstructor
@Tag(name = "Informações Auxiliares de Comunicações", description = "Endpoints para gerenciamento de informações auxiliares das comunicações")
public class ComunicacaoAuxController {

    private final ComunicacaoAuxService comunicacaoAuxService;

    @PostMapping
    @Operation(summary = "Criar nova informação auxiliar de comunicação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Informação auxiliar criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Comunicação não encontrada")
    })
    public ResponseEntity<ComunicacaoAuxResponseDTO> criar(
            @Parameter(description = "Dados da informação auxiliar", required = true)
            @Valid @RequestBody ComunicacaoAuxRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comunicacaoAuxService.criar(request));
    }

    @GetMapping("/comunicacao/{comunicacaoId}")
    @Operation(summary = "Buscar informação auxiliar por ID da comunicação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Informação auxiliar encontrada"),
        @ApiResponse(responseCode = "404", description = "Informação auxiliar não encontrada")
    })
    public ResponseEntity<ComunicacaoAuxResponseDTO> buscarPorComunicacaoId(
            @Parameter(description = "ID da comunicação", required = true)
            @PathVariable Integer comunicacaoId) {
        return ResponseEntity.ok(comunicacaoAuxService.buscarPorComunicacaoId(comunicacaoId));
    }

    @GetMapping("/projeto/{projetoId}")
    @Operation(summary = "Listar informações auxiliares por projeto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de informações auxiliares retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity<Page<ComunicacaoAuxResponseDTO>> listarPorProjeto(
            @Parameter(description = "ID do projeto", required = true)
            @PathVariable Integer projetoId,
            @Parameter(description = "Número da página (0..N)", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Quantidade de elementos por página", example = "10")
            @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "Critério de ordenação (dataCriacao,desc)")
            @RequestParam(defaultValue = "dataCriacao,desc") String sort) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sort.split(",")[0]).descending()
        );

        return ResponseEntity.ok(comunicacaoAuxService.listarPorProjeto(projetoId, pageable));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar informações auxiliares por cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de informações auxiliares retornada"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<List<ComunicacaoAuxResponseDTO>> listarPorCliente(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Integer clienteId) {
        return ResponseEntity.ok(comunicacaoAuxService.listarPorCliente(clienteId));
    }

    @DeleteMapping("/comunicacao/{comunicacaoId}")
    @Operation(summary = "Excluir informação auxiliar")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Informação auxiliar excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Informação auxiliar não encontrada")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @Parameter(description = "ID da comunicação", required = true)
            @PathVariable Integer comunicacaoId) {
        comunicacaoAuxService.deletar(comunicacaoId);
    }
}