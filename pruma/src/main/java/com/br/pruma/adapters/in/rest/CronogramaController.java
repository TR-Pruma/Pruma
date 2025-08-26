package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import com.br.pruma.application.service.CronogramaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pruma/v1/projetos/cronograma")
@Tag(name = "Cronograma", description = "Gerencia cronogramas de um projeto")
@RequiredArgsConstructor

public class CronogramaController {

    private final CronogramaService service;

    @Operation(summary = "Cria um cronograma para o projeto")
    @PostMapping
    public ResponseEntity<CronogramaResponseDTO> create(
            @PathVariable Integer projetoId,
            @Valid @RequestBody CronogramaRequestDTO body
    ) {
        CronogramaResponseDTO resp = service.create(
                new CronogramaRequestDTO(
                        projetoId,
                        body.dataInicio(),
                        body.dataFim()
                )
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resp.id())
                .toUri();

        return ResponseEntity.created(location).body(resp);
    }

    @Operation(summary = "Lista cronogramas do projeto")
    @GetMapping
    public ResponseEntity<List<CronogramaResponseDTO>> findAll(
            @PathVariable Integer projetoId
    ) {
        List<CronogramaResponseDTO> list = service.getAllByProjeto(projetoId);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Busca cronograma por ID dentro do projeto")
    @GetMapping("/{id}")
    public ResponseEntity<CronogramaResponseDTO> findById(
            @PathVariable Integer projetoId,
            @PathVariable Integer id
    ) {
        CronogramaResponseDTO resp = service.getByIdAndProjeto(projetoId, id);
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Atualiza cronograma dentro do projeto")
    @PutMapping("/{id}")
    public ResponseEntity<CronogramaResponseDTO> update(
            @PathVariable Integer projetoId,
            @PathVariable Integer id,
            @Valid @RequestBody CronogramaRequestDTO body
    ) {
        CronogramaResponseDTO resp = service.update(
                projetoId,
                id,
                new CronogramaRequestDTO(
                        projetoId,
                        body.dataInicio(),
                        body.dataFim()
                )
        );
        return ResponseEntity.ok(resp);
    }

    @Operation(summary = "Remove cronograma do projeto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer projetoId,
            @PathVariable Integer id
    ) {
        service.delete(projetoId, id);
        return ResponseEntity.noContent().build();
    }
}