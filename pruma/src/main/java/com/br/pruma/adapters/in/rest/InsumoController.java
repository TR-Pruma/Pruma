package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.InsumoRequestDTO;
import com.br.pruma.application.dto.response.InsumoResponseDTO;
import com.br.pruma.application.service.InsumoService;
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
@RequestMapping("/pruma/v1/insumos")
@Tag(name = "Insumo", description = "Gerencia insumos do sistema")
@RequiredArgsConstructor
public class InsumoController {

    private final InsumoService service;

    @Operation(summary = "Cria um novo insumo")
    @PostMapping
    public ResponseEntity<InsumoResponseDTO> create(
            @Valid @RequestBody InsumoRequestDTO dto
    ) {
        InsumoResponseDTO resp = service.create(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resp.id())
                .toUri();
        return ResponseEntity.created(location).body(resp);
    }
    @Operation(summary = "Lista todos os insumos")
    @GetMapping
    public ResponseEntity<List<InsumoResponseDTO>> findAll() {
        return ResponseEntity.ok(service.getAll());
    }
    @Operation(summary = "Busca insumo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<InsumoResponseDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
    @Operation(summary = "Atualiza um insumo existente")
    @PutMapping("/{id}")
    public ResponseEntity<InsumoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody InsumoRequestDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }
    @Operation(summary = "Remove um insumo pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
