package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.SolicitacaoMudancaRequestDTO;
import com.br.pruma.application.dto.response.SolicitacaoMudancaResponseDTO;
import com.br.pruma.application.dto.update.SolicitacaoMudancaUpdateDTO;
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

@Tag(name = "SolicitacaoMudanca", description = "Operações relacionadas a solicitações de mudança")
@RestController
@RequestMapping("/pruma/v1/solicitacoes-mudanca")
@RequiredArgsConstructor
public class SolicitacaoMudancaController {

    private final SolicitacaoMudancaService service;

    @Operation(summary = "Lista todas as solicitações de mudança")
    @GetMapping
    public ResponseEntity<List<SolicitacaoMudancaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista solicitações de mudança por obra")
    @GetMapping("/obra/{obraId}")
    public ResponseEntity<List<SolicitacaoMudancaResponseDTO>> listarPorObra(@PathVariable Integer obraId) {
        return ResponseEntity.ok(service.listByObra(obraId));
    }

    @Operation(summary = "Busca solicitação de mudança por ID")
    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoMudancaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria nova solicitação de mudança")
    @PostMapping
    public ResponseEntity<SolicitacaoMudancaResponseDTO> criar(@RequestBody @Valid SolicitacaoMudancaRequestDTO dto) {
        SolicitacaoMudancaResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza solicitação de mudança por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<SolicitacaoMudancaResponseDTO> atualizar(@PathVariable Integer id,
                                                                   @RequestBody @Valid SolicitacaoMudancaUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta solicitação de mudança por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
