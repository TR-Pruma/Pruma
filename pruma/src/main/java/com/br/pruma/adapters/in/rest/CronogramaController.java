package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import com.br.pruma.infra.repository.CronogramaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cronogramas")
@RequiredArgsConstructor
@Tag(name = "Cronogramas", description = "Endpoints para gerenciamento de cronogramas")
public class CronogramaController {

    private final CronogramaService cronogramaService;
    @PostMapping
    @Operation(summary = "Criar novo cronograma")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cronograma criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity<CronogramaResponseDTO> criar(
            @Parameter(description = "Dados do cronograma", required = true)
            @Valid @RequestBody CronogramaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cronogramaService.criar(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cronograma por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cronograma encontrado"),
        @ApiResponse(responseCode = "404", description = "Cronograma não encontrado")
    })
    public ResponseEntity<CronogramaResponseDTO> buscarPorId(
            @Parameter(description = "ID do cronograma", required = true)
            @PathVariable Integer id) {
        return ResponseEntity.ok(cronogramaService.buscarPorId(id));
    }

    @GetMapping("/projeto/{projetoId}")
    @Operation(summary = "Listar cronogramas por projeto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cronogramas retornada"),
        @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity<Page<CronogramaResponseDTO>> listarPorProjeto(
            @Parameter(description = "ID do projeto", required = true)
            @PathVariable Integer projetoId,
            @Parameter(description = "Número da página (0..N)", example = "0")
            @RequestParam(defaultValue = "0") Integer page,
            @Parameter(description = "Quantidade de elementos por página", example = "10")
            @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "Critério de ordenação (dataInicio,desc)")
            @RequestParam(defaultValue = "dataInicio,desc") String sort) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(sort.split(",")[0]).descending());
        return ResponseEntity.ok(cronogramaService.listarPorProjeto(projetoId, pageable));
    }

    @GetMapping("/periodo")
    @Operation(summary = "Listar cronogramas por período")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cronogramas retornada"),
        @ApiResponse(responseCode = "400", description = "Datas inválidas fornecidas")
    })
    public ResponseEntity<List<CronogramaResponseDTO>> listarPorPeriodo(
            @Parameter(description = "Data de início", example = "2025-08-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @Parameter(description = "Data de fim", example = "2025-12-31")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(cronogramaService.listarPorPeriodo(inicio, fim));
    }

    @GetMapping("/projeto/{projetoId}/ordenado")
    @Operation(summary = "Listar cronogramas por projeto ordenados por data de início")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cronogramas retornada"),
        @ApiResponse(responseCode = "404", description = "Projeto não encontrado")
    })
    public ResponseEntity<List<CronogramaResponseDTO>> listarPorProjetoOrdenado(
            @Parameter(description = "ID do projeto", required = true)
            @PathVariable Integer projetoId) {
        return ResponseEntity.ok(cronogramaService.listarPorProjetoOrdenado(projetoId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cronograma")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cronograma atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
        @ApiResponse(responseCode = "404", description = "Cronograma não encontrado")
    })
    public ResponseEntity<CronogramaResponseDTO> atualizar(
            @Parameter(description = "ID do cronograma", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Novos dados do cronograma", required = true)
            @Valid @RequestBody CronogramaRequestDTO request) {
        return ResponseEntity.ok(cronogramaService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cronograma")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cronograma excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cronograma não encontrado")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(
            @Parameter(description = "ID do cronograma", required = true)
            @PathVariable Integer id) {
        cronogramaService.deletar(id);
    }
}

