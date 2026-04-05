package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.OrcamentoRequestDTO;
import com.br.pruma.application.dto.response.OrcamentoResponseDTO;
import com.br.pruma.application.dto.update.OrcamentoUpdateDTO;
import com.br.pruma.application.service.OrcamentoService;
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
@RequestMapping("/pruma/v1/orcamentos")
@Tag(name = "Orçamento", description = "Gerencia orçamentos")
@RequiredArgsConstructor
public class OrcamentoController {

    private final OrcamentoService service;

    @Operation(summary = "Cria um novo orçamento")
    @PostMapping
    public ResponseEntity<OrcamentoResponseDTO> create(@Valid @RequestBody OrcamentoRequestDTO request) {
        OrcamentoResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os orçamentos")
    @GetMapping
    public ResponseEntity<List<OrcamentoResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca orçamento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrcamentoResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista orçamentos com paginação")
    @GetMapping("/page")
    public ResponseEntity<Page<OrcamentoResponseDTO>> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @Operation(summary = "Atualiza parcialmente um orçamento")
    @PatchMapping("/{id}")
    public ResponseEntity<OrcamentoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody OrcamentoUpdateDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Substitui completamente um orçamento (PUT)")
    @PutMapping("/{id}")
    public ResponseEntity<OrcamentoResponseDTO> replace(
            @PathVariable Integer id,
            @Valid @RequestBody OrcamentoRequestDTO request) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @Operation(summary = "Exclui permanentemente um orçamento")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
