package com.br.pruma.adapters.in.rest;


import com.br.pruma.application.dto.request.StatusEquipamentoRequestDTO;
import com.br.pruma.application.dto.response.StatusEquipamentoResponseDTO;
import com.br.pruma.application.service.StatusEquipamentoService;
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
@RequestMapping("/pruma/v1/status-equipamento")
@Tag(name = "Status de Equipamento", description = "Gerencia status de equipamentos")
@RequiredArgsConstructor
public class StatusEquipamentoController {

    private final StatusEquipamentoService service;

    @Operation(summary = "Cria um novo status de equipamento")
    @PostMapping
    public ResponseEntity<StatusEquipamentoResponseDTO> create(@Valid @RequestBody StatusEquipamentoRequestDTO request) {
        StatusEquipamentoResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os status de equipamento")
    @GetMapping
    public ResponseEntity<List<StatusEquipamentoResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Obtém um status de equipamento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<StatusEquipamentoResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Obtém um status de equipamento por descrição")
    @GetMapping("/descricao/{descricao}")
    public ResponseEntity<StatusEquipamentoResponseDTO> getByDescricao(@PathVariable String descricao) {
        return ResponseEntity.ok(service.getByDescricao(descricao));
    }

    @Operation(summary = "Atualiza um status de equipamento")
    @PutMapping("/{id}")
    public ResponseEntity<StatusEquipamentoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody StatusEquipamentoRequestDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Exclui permanentemente um status de equipamento")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
