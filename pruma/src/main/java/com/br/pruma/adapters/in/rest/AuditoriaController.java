package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.AuditoriaRequestDTO;
import com.br.pruma.application.dto.response.AuditoriaResponseDTO;
import com.br.pruma.application.dto.update.AuditoriaUpdateDTO;
import com.br.pruma.application.service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return ResponseEntity.ok(auditoriaService.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar auditoria por ID")
    public ResponseEntity<AuditoriaResponseDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(auditoriaService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Criar nova auditoria")
    public ResponseEntity<AuditoriaResponseDTO> criar(@Valid @RequestBody AuditoriaRequestDTO dto) {
        AuditoriaResponseDTO salva = auditoriaService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salva.getId()).toUri();
        return ResponseEntity.created(location).body(salva);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar auditoria existente")
    public ResponseEntity<AuditoriaResponseDTO> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody AuditoriaUpdateDTO dto) {
        return ResponseEntity.ok(auditoriaService.update(id, dto));
    }

    @GetMapping("/paginado")
    @Operation(summary = "Listar auditorias paginadas")
    public ResponseEntity<Page<AuditoriaResponseDTO>> listarPaginado(Pageable pageable) {
        return ResponseEntity.ok(auditoriaService.list(pageable));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar auditoria por ID")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        auditoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
