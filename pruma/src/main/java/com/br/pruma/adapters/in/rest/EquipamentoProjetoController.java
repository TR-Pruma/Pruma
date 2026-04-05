package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.EquipamentoProjetoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoProjetoResponseDTO;
import com.br.pruma.application.dto.update.EquipamentoProjetoUpdateDTO;
import com.br.pruma.application.service.EquipamentoProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "EquipamentoProjeto", description = "Operações relacionadas a equipamentos de projetos")
@RestController
@RequestMapping("/pruma/v1/equipamentos-projeto")
@RequiredArgsConstructor
public class EquipamentoProjetoController {

    private final EquipamentoProjetoService service;

    @Operation(summary = "Lista todos os equipamentos-projeto")
    @GetMapping
    public ResponseEntity<List<EquipamentoProjetoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca equipamento-projeto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoProjetoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria novo equipamento-projeto")
    @PostMapping
    public ResponseEntity<EquipamentoProjetoResponseDTO> criar(@RequestBody @Valid EquipamentoProjetoRequestDTO dto) {
        EquipamentoProjetoResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza equipamento-projeto por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<EquipamentoProjetoResponseDTO> atualizar(@PathVariable Integer id,
                                                                   @RequestBody @Valid EquipamentoProjetoUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta equipamento-projeto por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
