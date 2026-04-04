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

@Tag(name = "StatusEquipamento", description = "Operações relacionadas a status de equipamento")
@RestController
@RequestMapping("/pruma/v1/status-equipamento")
@RequiredArgsConstructor
public class StatusEquipamentoController {

    private final StatusEquipamentoService service;

    @Operation(summary = "Lista todos os status")
    @GetMapping
    public ResponseEntity<List<StatusEquipamentoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca status por ID")
    @GetMapping("/{id}")
    public ResponseEntity<StatusEquipamentoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria novo status")
    @PostMapping
    public ResponseEntity<StatusEquipamentoResponseDTO> criar(@RequestBody @Valid StatusEquipamentoRequestDTO dto) {
        StatusEquipamentoResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.id()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza status por ID")
    @PutMapping("/{id}")
    public ResponseEntity<StatusEquipamentoResponseDTO> atualizar(@PathVariable Integer id,
                                                                   @RequestBody @Valid StatusEquipamentoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta status por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
