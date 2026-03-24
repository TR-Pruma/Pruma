package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.PreObraRequestDTO;
import com.br.pruma.application.dto.response.PreObraResponseDTO;
import com.br.pruma.application.dto.update.PreObraUpdateDTO;
import com.br.pruma.application.service.PreObraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pruma/v1/pre-obras")
@Tag(name = "PreObra", description = "Gerencia registros de pré-obra")
@RequiredArgsConstructor
public class PreObraController {

    private final PreObraService service;

    @Operation(summary = "Cria um novo registro de pré-obra")
    @PostMapping
    public ResponseEntity<PreObraResponseDTO> create(
            @Valid @RequestBody PreObraRequestDTO request
    ) {
        PreObraResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os registros de pré-obra")
    @GetMapping
    public ResponseEntity<List<PreObraResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista registros de pré-obra com paginação")
    @GetMapping("/page")
    public ResponseEntity<?> list(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @Operation(summary = "Obtém um registro de pré-obra por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PreObraResponseDTO> getById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista registros de pré-obra por obra")
    @GetMapping("/obra/{obraId}")
    public ResponseEntity<List<PreObraResponseDTO>> listByObra(
            @PathVariable Integer obraId
    ) {
        return ResponseEntity.ok(service.listByObra(obraId));
    }

    @Operation(summary = "Busca registros por descrição (opcionalmente paginado)")
    @GetMapping("/search")
    public ResponseEntity<?> searchByDescricao(
            @RequestParam(name = "descricao", required = false, defaultValue = "") String descricao,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        if (descricao == null || descricao.isBlank()) {
            return ResponseEntity.ok(service.list(pageable));
        }
        return ResponseEntity.ok(service.searchByDescricao(descricao, pageable));
    }

    @Operation(summary = "Atualiza parcialmente um registro de pré-obra")
    @PutMapping("/{id}")
    public ResponseEntity<PreObraResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody PreObraUpdateDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Substitui completamente os campos permitidos de um registro (PUT semantics)")
    @PostMapping("/{id}/replace")
    public ResponseEntity<PreObraResponseDTO> replace(
            @PathVariable Integer id,
            @Valid @RequestBody PreObraRequestDTO request
    ) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @Operation(summary = "Exclui permanentemente um registro de pré-obra")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}