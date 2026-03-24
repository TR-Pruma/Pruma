package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ObraRequestDTO;
import com.br.pruma.application.dto.response.ObraResponseDTO;
import com.br.pruma.application.dto.update.ObraUpdateDTO;
import com.br.pruma.application.service.ObraService;
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
@RequestMapping("/pruma/v1/obras")
@Tag(name = "Obra", description = "Gerencia obras")
@RequiredArgsConstructor
public class ObraController {

    private final ObraService service;

    @Operation(summary = "Cria uma nova obra")
    @PostMapping
    public ResponseEntity<ObraResponseDTO> create(
            @Valid @RequestBody ObraRequestDTO request
    ) {
        ObraResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todas as obras")
    @GetMapping
    public ResponseEntity<List<ObraResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca obra por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ObraResponseDTO> getById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista obras por projeto")
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<ObraResponseDTO>> listByProjeto(
            @PathVariable Integer projetoId
    ) {
        return ResponseEntity.ok(service.listByProjeto(projetoId));
    }

    @Operation(summary = "Busca obras por descrição (busca simples ou paginada)")
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

    @Operation(summary = "Atualiza parcialmente uma obra existente (PATCH semantics implemented via DTO)")
    @PutMapping("/{id}")
    public ResponseEntity<ObraResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ObraUpdateDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Substitui uma obra (PUT) — substituição completa dos campos permitidos")
    @PostMapping("/{id}/replace")
    public ResponseEntity<ObraResponseDTO> replace(
            @PathVariable Integer id,
            @Valid @RequestBody ObraRequestDTO request
    ) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @Operation(summary = "Exclui permanentemente uma obra")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
