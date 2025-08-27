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
@Tag(name = "Orcamento", description = "Gerencia orçamentos")
@RequiredArgsConstructor
public class OrcamentoController {

    private final OrcamentoService service;

    @Operation(summary = "Cria um novo orçamento")
    @PostMapping
    public ResponseEntity<OrcamentoResponseDTO> create(
            @Valid @RequestBody OrcamentoRequestDTO request
    ) {
        OrcamentoResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }
    @Operation(summary = "Lista orçamentos (opcionalmente filtra por projeto)")
    @GetMapping
    public ResponseEntity<List<OrcamentoResponseDTO>> list(
            @RequestParam(value = "projetoId", required = false) Integer projetoId
    ) {
        List<OrcamentoResponseDTO> list = (projetoId == null)
                ? service.listAll()
                : service.listByProjeto(projetoId);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Busca um orçamento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<OrcamentoResponseDTO> getById(
            @PathVariable Integer id
    ) {
        OrcamentoResponseDTO response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza um orçamento existente")
    @PutMapping("/{id}")
    public ResponseEntity<OrcamentoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody OrcamentoUpdateDTO request
    ) {
        OrcamentoResponseDTO response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove um orçamento")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
