package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import com.br.pruma.application.dto.update.CronogramaUpdateDTO;
import com.br.pruma.application.service.CronogramaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Cronograma", description = "Operações relacionadas a cronogramas")
@RestController
@RequestMapping("/pruma/v1/cronogramas")
@RequiredArgsConstructor
public class CronogramaController {

    private final CronogramaService service;

    @Operation(summary = "Lista todos os cronogramas")
    @GetMapping
    public ResponseEntity<List<CronogramaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista cronogramas por projeto")
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<CronogramaResponseDTO>> listarPorProjeto(@PathVariable Integer projetoId) {
        return ResponseEntity.ok(service.listByProjeto(projetoId));
    }

    @Operation(summary = "Busca cronograma por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CronogramaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria novo cronograma")
    @PostMapping
    public ResponseEntity<CronogramaResponseDTO> criar(@RequestBody @Valid CronogramaRequestDTO dto) {
        CronogramaResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza cronograma por ID (patch parcial)")
    @PatchMapping("/{id}")
    public ResponseEntity<CronogramaResponseDTO> atualizar(@PathVariable Integer id,
                                                           @RequestBody @Valid CronogramaUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta cronograma por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
