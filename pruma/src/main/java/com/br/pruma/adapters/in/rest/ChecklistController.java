package com.br.pruma.adapters.in.rest;
import com.br.pruma.application.dto.request.ChecklistRequestDTO;
import com.br.pruma.application.dto.response.ChecklistResponseDTO;
import com.br.pruma.application.service.ChecklistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pruma/v1/checklists")
@Tag(name = "Checklists", description = "Operações de gerenciamento de checklists")
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService service;

    @GetMapping
    @Operation(summary = "Listar todos os checklists")
    public ResponseEntity<List<ChecklistResponseDTO>> findAll() {
        List<ChecklistResponseDTO> dtos = service.findAll();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar checklist por ID")
    public ResponseEntity<ChecklistResponseDTO> findById(
            @PathVariable("id") Integer id
    ) {
        ChecklistResponseDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Criar um novo checklist")
    public ResponseEntity<ChecklistResponseDTO> create(
            @Valid @RequestBody ChecklistRequestDTO dto
    ) {
        ChecklistResponseDTO created = service.create(dto);
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
    @Operation(summary = "Atualizar checklist existente")
    public ResponseEntity<ChecklistResponseDTO> update(
            @PathVariable("id") Integer id,
            @Valid @RequestBody ChecklistRequestDTO dto
    ) {
        ChecklistResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir checklist por ID")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
