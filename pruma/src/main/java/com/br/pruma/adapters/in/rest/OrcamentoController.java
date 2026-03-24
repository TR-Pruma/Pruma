package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.OrcamentoRequestDTO;
import com.br.pruma.application.dto.response.OrcamentoResponseDTO;
import com.br.pruma.application.dto.update.OrcamentoUpdateDTO;
import com.br.pruma.application.service.OrcamentoService;
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
@RequestMapping("/pruma/v1/orcamentos")
@Tag(name = "Orcamento", description = "Gerencia orçamentos de projetos")
@RequiredArgsConstructor
public class OrcamentoController {

    private final OrcamentoService service;

    @Operation(summary = "Cria um novo orçamento")
    @PostMapping
    public ResponseEntity<OrcamentoResponseDTO> create(
            @Valid @RequestBody OrcamentoRequestDTO request
    ) {
        OrcamentoResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os orçamentos")
    @GetMapping
    public ResponseEntity<List<OrcamentoResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca orçamento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrcamentoResponseDTO> getById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista orçamentos por projeto")
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<OrcamentoResponseDTO>> listByProjeto(
            @PathVariable Integer projetoId
    ) {
        return ResponseEntity.ok(service.listByProjeto(projetoId));
    }

    @Operation(summary = "Lista orçamentos por empresa (CNPJ)")
    @GetMapping("/empresa/{empresaCnpj}")
    public ResponseEntity<List<OrcamentoResponseDTO>> listByEmpresa(
            @PathVariable String empresaCnpj
    ) {
        return ResponseEntity.ok(service.listByEmpresa(empresaCnpj));
    }

    @Operation(summary = "Lista orçamentos por status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrcamentoResponseDTO>> listByStatus(
            @PathVariable String status
    ) {
        return ResponseEntity.ok(service.listByStatus(status));
    }

    @Operation(summary = "Atualiza parcialmente um orçamento existente")
    @PutMapping("/{id}")
    public ResponseEntity<OrcamentoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody OrcamentoUpdateDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Exclui logicamente um orçamento")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}