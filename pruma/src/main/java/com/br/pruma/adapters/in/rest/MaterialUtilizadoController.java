package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.MaterialUtilizadoRequestDTO;
import com.br.pruma.application.dto.response.MaterialUtilizadoResponseDTO;
import com.br.pruma.application.dto.update.MaterialUtilizadoUpdateDTO;
import com.br.pruma.application.service.MaterialUtilizadoService;
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
@RequestMapping("/pruma/v1/material-utilizados")
@Tag(name = "MaterialUtilizado", description = "Gerencia materiais utilizados em atividades")
@RequiredArgsConstructor
public class MaterialUtilizadoController {

    private final MaterialUtilizadoService service;

    @Operation(summary = "Cria uma nova associação de material utilizado em atividade")
    @PostMapping
    public ResponseEntity<MaterialUtilizadoResponseDTO> create(
            @Valid @RequestBody MaterialUtilizadoRequestDTO request
    ) {
        MaterialUtilizadoResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todas as associações de material utilizado")
    @GetMapping
    public ResponseEntity<List<MaterialUtilizadoResponseDTO>> listAll() {
        List<MaterialUtilizadoResponseDTO> list = service.listAll();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Consulta uma associação de material utilizado pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<MaterialUtilizadoResponseDTO> getById(
            @PathVariable Integer id
    ) {
        MaterialUtilizadoResponseDTO response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Lista associações de material utilizado por atividade")
    @GetMapping("/atividade/{atividadeId}")
    public ResponseEntity<List<MaterialUtilizadoResponseDTO>> listByAtividade(
            @PathVariable Integer atividadeId
    ) {
        List<MaterialUtilizadoResponseDTO> list = service.listByAtividade(atividadeId);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Lista associações de material utilizado por material")
    @GetMapping("/material/{materialId}")
    public ResponseEntity<List<MaterialUtilizadoResponseDTO>> listByMaterial(
            @PathVariable Integer materialId
    ) {
        List<MaterialUtilizadoResponseDTO> list = service.listByMaterial(materialId);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Atualiza parcialmente uma associação de material utilizado existente")
    @PutMapping("/{id}")
    public ResponseEntity<MaterialUtilizadoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody MaterialUtilizadoUpdateDTO request
    ) {
        MaterialUtilizadoResponseDTO response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove uma associação de material utilizado pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

