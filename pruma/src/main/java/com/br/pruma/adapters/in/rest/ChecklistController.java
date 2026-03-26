package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ChecklistRequestDTO;
import com.br.pruma.application.dto.response.ChecklistResponseDTO;
import com.br.pruma.application.dto.update.ChecklistUpdateDTO;
import com.br.pruma.application.service.ChecklistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pruma/v1/checklists")
@Tag(name = "Checklist", description = "Gerencia checklists")
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService service;

    @Operation(summary = "Cria um novo checklist")
    @PostMapping
    public ResponseEntity<ChecklistResponseDTO> create(@Valid @RequestBody ChecklistRequestDTO request) {
        ChecklistResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os checklists")
    @GetMapping
    public ResponseEntity<List<ChecklistResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca checklist por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ChecklistResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista checklists com paginação")
    @GetMapping("/page")
    public ResponseEntity<Page<ChecklistResponseDTO>> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @Operation(summary = "Atualiza parcialmente um checklist")
    @PatchMapping("/{id}")
    public ResponseEntity<ChecklistResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ChecklistUpdateDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Substitui completamente um checklist (PUT)")
    @PutMapping("/{id}")
    public ResponseEntity<ChecklistResponseDTO> replace(
            @PathVariable Integer id,
            @Valid @RequestBody ChecklistRequestDTO request) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @Operation(summary = "Exclui permanentemente um checklist")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
