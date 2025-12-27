package com.br.pruma.adapters.in.rest;


import com.br.pruma.application.dto.request.SolicitacaoMudancaRequestDTO;
import com.br.pruma.application.dto.response.SolicitacaoMudancaResponseDTO;
import com.br.pruma.application.service.SolicitacaoMudancaService;
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
@RequestMapping("/pruma/v1/solicitacoes-mudanca")
@Tag(name = "Solicitação de Mudança", description = "Gerencia solicitações de mudança de projetos")
@RequiredArgsConstructor
public class SolicitacaoMudancaController {

    private final SolicitacaoMudancaService service;

    @Operation(summary = "Cria uma nova solicitação de mudança")
    @PostMapping
    public ResponseEntity<SolicitacaoMudancaResponseDTO> create(@Valid @RequestBody SolicitacaoMudancaRequestDTO request) {
        SolicitacaoMudancaResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todas as solicitações de mudança")
    @GetMapping
    public ResponseEntity<List<SolicitacaoMudancaResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista solicitações de mudança de um projeto específico")
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<SolicitacaoMudancaResponseDTO>> listByProjeto(@PathVariable Integer projetoId) {
        return ResponseEntity.ok(service.listByProjetoId(projetoId));
    }

    @Operation(summary = "Lista solicitações de mudança por status específico")
    @GetMapping("/status/{statusSolicitacaoId}")
    public ResponseEntity<List<SolicitacaoMudancaResponseDTO>> listByStatus(@PathVariable Integer statusSolicitacaoId) {
        return ResponseEntity.ok(service.listByStatusSolicitacaoId(statusSolicitacaoId));
    }

    @Operation(summary = "Obtém uma solicitação de mudança por ID")
    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoMudancaResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Atualiza uma solicitação de mudança")
    @PutMapping("/{id}")
    public ResponseEntity<SolicitacaoMudancaResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody SolicitacaoMudancaRequestDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Exclui permanentemente uma solicitação de mudança")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
