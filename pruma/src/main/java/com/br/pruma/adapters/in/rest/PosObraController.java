package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.PosObraRequestDTO;
import com.br.pruma.application.dto.response.PosObraResponseDTO;
import com.br.pruma.application.dto.update.PosObraUpdateDTO;
import com.br.pruma.application.service.PosObraService;
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
@RequestMapping("/pruma/v1/pos-obras")
@Tag(name = "PosObra", description = "Gerencia registros pós-obra")
@RequiredArgsConstructor
public class PosObraController {

    private final PosObraService service;

    @Operation(summary = "Cria um novo registro de pós-obra")
    @PostMapping
    public ResponseEntity<PosObraResponseDTO> create(
            @Valid @RequestBody PosObraRequestDTO request
    ) {
        PosObraResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os registros de pós-obra")
    @GetMapping
    public ResponseEntity<List<PosObraResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca pós-obra por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PosObraResponseDTO> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista registros de pós-obra por obra")
    @GetMapping("/obra/{obraId}")
    public ResponseEntity<List<PosObraResponseDTO>> listByObra(
            @PathVariable Long obraId
    ) {
        return ResponseEntity.ok(service.listByObra(obraId));
    }

    @Operation(summary = "Atualiza parcialmente um registro de pós-obra")
    @PutMapping("/{id}")
    public ResponseEntity<PosObraResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PosObraUpdateDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Exclui permanentemente um registro de pós-obra")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}