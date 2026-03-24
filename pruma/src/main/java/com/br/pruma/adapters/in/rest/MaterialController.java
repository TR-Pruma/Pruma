package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.MaterialRequestDTO;
import com.br.pruma.application.dto.response.MaterialResponseDTO;
import com.br.pruma.application.dto.update.MaterialUpdateDTO;
import com.br.pruma.application.service.MaterialService;
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
@RequestMapping("/pruma/v1/materiais")
@Tag(name = "Material", description = "Gerencia materiais")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService service;

    @Operation(summary = "Cria um novo material")
    @PostMapping
    public ResponseEntity<MaterialResponseDTO> create(
            @Valid @RequestBody MaterialRequestDTO request
    ) {
        MaterialResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os materiais")
    @GetMapping
    public ResponseEntity<List<MaterialResponseDTO>> listAll() {
        List<MaterialResponseDTO> list = service.listAll();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Busca um material por ID")
    @GetMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> getById(
            @PathVariable Integer id
    ) {
        MaterialResponseDTO response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza um material existente")
    @PutMapping("/{id}")
    public ResponseEntity<MaterialResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody MaterialUpdateDTO request
    ) {
        MaterialResponseDTO response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove um material por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}