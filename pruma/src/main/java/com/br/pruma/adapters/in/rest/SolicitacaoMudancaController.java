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

@Tag(name = "SolicitacaoMudanca", description = "Operações relacionadas a solicitações de mudança")
@RestController
@RequestMapping("/pruma/v1/solicitacoes-mudanca")
@RequiredArgsConstructor
public class SolicitacaoMudancaController {

    private final SolicitacaoMudancaService service;

    @Operation(summary = "Lista todas as solicitações")
    @GetMapping
    public ResponseEntity<List<SolicitacaoMudancaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista solicitações por projeto")
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<SolicitacaoMudancaResponseDTO>> listarPorProjeto(@PathVariable Integer projetoId) {
        return ResponseEntity.ok(service.listByProjeto(projetoId));
    }

    @Operation(summary = "Busca solicitação por ID")
    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoMudancaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria nova solicitação")
    @PostMapping
    public ResponseEntity<SolicitacaoMudancaResponseDTO> criar(@RequestBody @Valid SolicitacaoMudancaRequestDTO dto) {
        SolicitacaoMudancaResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.id()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza solicitação por ID")
    @PutMapping("/{id}")
    public ResponseEntity<SolicitacaoMudancaResponseDTO> atualizar(@PathVariable Integer id,
                                                                    @RequestBody @Valid SolicitacaoMudancaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta solicitação por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
