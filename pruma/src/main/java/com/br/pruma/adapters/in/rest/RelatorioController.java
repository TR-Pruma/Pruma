package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.RelatorioRequestDTO;
import com.br.pruma.application.dto.response.RelatorioResponseDTO;
import com.br.pruma.application.service.RelatorioService;
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
@RequestMapping("/pruma/v1/relatorios")
@Tag(name = "Relatorio", description = "Gerencia relatórios de obras")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService service;

    @Operation(summary = "Cria um novo relatório")
    @PostMapping
    public ResponseEntity<RelatorioResponseDTO> create(@Valid @RequestBody RelatorioRequestDTO request) {
        RelatorioResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id()) // se usar classe em vez de record, troque para getId()
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os relatórios")
    @GetMapping
    public ResponseEntity<List<RelatorioResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista relatórios de uma obra específica")
    @GetMapping("/obra/{obraId}")
    public ResponseEntity<List<RelatorioResponseDTO>> listByObra(@PathVariable Integer obraId) {
        return ResponseEntity.ok(service.listByObraId(obraId));
    }

    @Operation(summary = "Obtém um relatório por ID")
    @GetMapping("/{id}")
    public ResponseEntity<RelatorioResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Atualiza parcialmente um relatório")
    @PutMapping("/{id}")
    public ResponseEntity<RelatorioResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody RelatorioRequestDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Exclui permanentemente um relatório")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
