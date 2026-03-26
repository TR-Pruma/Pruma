package com.br.pruma.adapters.in.rest;


import com.br.pruma.application.dto.request.SubContratoRequestDTO;
import com.br.pruma.application.dto.response.SubContratoResponseDTO;
import com.br.pruma.application.service.SubContratoService;
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
@RequestMapping("/pruma/v1/subcontratos")
@Tag(name = "Subcontrato", description = "Gerencia subcontratos de projetos")
@RequiredArgsConstructor
public class SubContratoController {

    private final SubContratoService service;

    @Operation(summary = "Cria um novo subcontrato")
    @PostMapping
    public ResponseEntity<SubContratoResponseDTO> create(@Valid @RequestBody SubContratoRequestDTO request) {
        SubContratoResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os subcontratos")
    @GetMapping
    public ResponseEntity<List<SubContratoResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista subcontratos de um cliente específico")
    @GetMapping("/cliente/{clienteCpf}")
    public ResponseEntity<List<SubContratoResponseDTO>> listByClienteCpf(@PathVariable String clienteCpf) {
        return ResponseEntity.ok(service.listByClienteCpf(clienteCpf));
    }

    @Operation(summary = "Lista subcontratos de um projeto específico")
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<SubContratoResponseDTO>> listByProjeto(@PathVariable Integer projetoId) {
        return ResponseEntity.ok(service.listByProjetoId(projetoId));
    }

    @Operation(summary = "Lista subcontratos de um cliente em um projeto específico")
    @GetMapping("/cliente/{clienteCpf}/projeto/{projetoId}")
    public ResponseEntity<List<SubContratoResponseDTO>> listByClienteCpfAndProjetoId(
            @PathVariable String clienteCpf,
            @PathVariable Integer projetoId) {
        return ResponseEntity.ok(service.listByClienteCpfAndProjetoId(clienteCpf, projetoId));
    }

    @Operation(summary = "Obtém um subcontrato por ID")
    @GetMapping("/{id}")
    public ResponseEntity<SubContratoResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Atualiza um subcontrato")
    @PutMapping("/{id}")
    public ResponseEntity<SubContratoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody SubContratoRequestDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Exclui permanentemente um subcontrato")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
