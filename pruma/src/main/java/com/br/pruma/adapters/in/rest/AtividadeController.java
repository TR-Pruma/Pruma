package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.AtividadeRequestDTO;
import com.br.pruma.application.dto.response.AtividadeResponseDTO;
import com.br.pruma.application.service.AtividadeService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pruma/v1/atividades")
@Tag(name = "Atividades", description = "Operações de gerenciamento de atividades")
@RequiredArgsConstructor
public class AtividadeController {

    private final AtividadeService service;

    @GetMapping
    @Operation(summary = "Listar todas as atividades")
    public ResponseEntity<List<AtividadeResponseDTO>> findAll() {
        List<AtividadeResponseDTO> dtos = service.findAll();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar atividade por ID")
    public ResponseEntity<AtividadeResponseDTO> findById(
            @PathVariable Integer id
    ) {
        AtividadeResponseDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Criar nova atividade")
    public ResponseEntity<AtividadeResponseDTO> create(
            @Valid @RequestBody AtividadeRequestDTO dto
    ) {
        AtividadeResponseDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar atividade existente")
    public ResponseEntity<AtividadeResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AtividadeRequestDTO dto
    ) {
        AtividadeResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar atividade por ID")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

