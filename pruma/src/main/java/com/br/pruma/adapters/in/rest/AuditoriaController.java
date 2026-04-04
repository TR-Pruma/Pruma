package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.AuditoriaRequestDTO;
import com.br.pruma.application.dto.response.AuditoriaResponseDTO;
import com.br.pruma.application.service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pruma/v1/auditorias")
@Tag(name = "Auditorias", description = "Operações de auditoria")
@RequiredArgsConstructor
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    @GetMapping
    @Operation(summary = "Listar auditorias")
    public ResponseEntity<List<AuditoriaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(auditoriaService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar auditoria por ID")
    public ResponseEntity<AuditoriaResponseDTO> buscarPorId(@PathVariable UUID id) {
        return auditoriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar nova auditoria")
    public ResponseEntity<AuditoriaResponseDTO> criar(@Valid @RequestBody AuditoriaRequestDTO dto) {
        AuditoriaResponseDTO salva = auditoriaService.criar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salva.id()).toUri();
        return ResponseEntity.created(location).body(salva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar auditoria existente")
    public ResponseEntity<AuditoriaResponseDTO> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody AuditoriaRequestDTO dto) {
        return auditoriaService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar auditoria por ID")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        auditoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
