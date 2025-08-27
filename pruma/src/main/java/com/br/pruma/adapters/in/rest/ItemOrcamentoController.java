package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ItemOrcamentoRequestDTO;
import com.br.pruma.application.dto.response.ItemOrcamentoResponseDTO;
import com.br.pruma.application.dto.update.ItemOrcamentoUpdateDTO;
import com.br.pruma.application.service.ItemOrcamentoService;
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
@RequestMapping("/pruma/v1/orcamentos/{orcamentoId}/itens")
@Tag(name = "ItemOrcamento", description = "Gerencia itens de orçamento")
@RequiredArgsConstructor
public class ItemOrcamentoController {
    private final ItemOrcamentoService service;


    @Operation(summary = "Cria um novo item de orçamento")
    @PostMapping
    public ResponseEntity<ItemOrcamentoResponseDTO> create(
            @PathVariable Integer orcamentoId,
            @Valid @RequestBody ItemOrcamentoRequestDTO request
    ) {
        // Sobrescreve orcamentoId vindo da URL
        var dto = ItemOrcamentoRequestDTO.builder()
                .orcamentoId(orcamentoId)
                .descricao(request.getDescricao())
                .quantidade(request.getQuantidade())
                .valorUnitario(request.getValorUnitario())
                .build();

        ItemOrcamentoResponseDTO response = service.create(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{itemId}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
    @Operation(summary = "Lista todos os itens de um orçamento")
    @GetMapping
    public ResponseEntity<List<ItemOrcamentoResponseDTO>> listByOrcamento(
            @PathVariable Integer orcamentoId
    ) {
        List<ItemOrcamentoResponseDTO> list = service.listByOrcamento(orcamentoId);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Busca um item de orçamento por ID")
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemOrcamentoResponseDTO> getById(
            @PathVariable Integer orcamentoId,
            @PathVariable Integer itemId
    ) {
        ItemOrcamentoResponseDTO response = service.getById(itemId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza um item de orçamento existente")
    @PutMapping("/{itemId}")
    public ResponseEntity<ItemOrcamentoResponseDTO> update(
            @PathVariable Integer orcamentoId,
            @PathVariable Integer itemId,
            @Valid @RequestBody ItemOrcamentoUpdateDTO request
    ) {
        ItemOrcamentoResponseDTO response = service.update(itemId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove um item de orçamento")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer orcamentoId,
            @PathVariable Integer itemId
    ) {
        service.delete(itemId);
        return ResponseEntity.noContent().build();
    }
}
