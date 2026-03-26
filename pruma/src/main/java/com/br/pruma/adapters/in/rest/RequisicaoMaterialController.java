package com.br.pruma.adapters.in.rest;


import com.br.pruma.application.dto.request.RequisicaoMaterialRequestDTO;
import com.br.pruma.application.dto.response.RequisicaoMaterialResponseDTO;
import com.br.pruma.application.service.RequisicaoMaterialService;
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
@RequestMapping("/pruma/v1/requisicoes-material")
@Tag(name = "Requisição de Material", description = "Gerencia requisições de material de obras")
@RequiredArgsConstructor
public class RequisicaoMaterialController {

    private final RequisicaoMaterialService service;

    @Operation(summary = "Cria uma nova requisição de material")
    @PostMapping
    public ResponseEntity<RequisicaoMaterialResponseDTO> create(@Valid @RequestBody RequisicaoMaterialRequestDTO request) {
        RequisicaoMaterialResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todas as requisições de material")
    @GetMapping
    public ResponseEntity<List<RequisicaoMaterialResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista requisições de material de uma obra específica")
    @GetMapping("/obra/{obraId}")
    public ResponseEntity<List<RequisicaoMaterialResponseDTO>> listByObra(@PathVariable Integer obraId) {
        return ResponseEntity.ok(service.listByObraId(obraId));
    }

    @Operation(summary = "Lista requisições de um material específico")
    @GetMapping("/material/{materialId}")
    public ResponseEntity<List<RequisicaoMaterialResponseDTO>> listByMaterial(@PathVariable Integer materialId) {
        return ResponseEntity.ok(service.listByMaterialId(materialId));
    }

    @Operation(summary = "Obtém uma requisição de material por ID")
    @GetMapping("/{id}")
    public ResponseEntity<RequisicaoMaterialResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Atualiza uma requisição de material")
    @PutMapping("/{id}")
    public ResponseEntity<RequisicaoMaterialResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody RequisicaoMaterialRequestDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Exclui permanentemente uma requisição de material")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}