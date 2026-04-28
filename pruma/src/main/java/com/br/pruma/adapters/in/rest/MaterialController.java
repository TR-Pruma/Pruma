package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.MaterialRequestDTO;
import com.br.pruma.application.dto.response.MaterialResponseDTO;
import com.br.pruma.application.dto.update.MaterialUpdateDTO;
import com.br.pruma.application.service.MaterialService;
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
@RequestMapping("/pruma/v1/materiais")
@Tag(name = "Material", description = "Gerencia materiais")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService service;

    @Operation(summary = "Cria um novo material")
    @PostMapping
    public ResponseEntity<MaterialResponseDTO> create(@Valid @RequestBody MaterialRequestDTO request) {
        MaterialResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os materiais")
    @GetMapping
    public ResponseEntity<List<MaterialResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca material por ID")
    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista materiais com paginação")
    @GetMapping("/page")
    public ResponseEntity<Page<MaterialResponseDTO>> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @Operation(summary = "Atualiza parcialmente um material")
    @PatchMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody MaterialUpdateDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Substitui completamente um material (PUT)")
    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> replace(
            @PathVariable Integer id,
            @Valid @RequestBody MaterialRequestDTO request) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @Operation(summary = "Exclui permanentemente um material")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
