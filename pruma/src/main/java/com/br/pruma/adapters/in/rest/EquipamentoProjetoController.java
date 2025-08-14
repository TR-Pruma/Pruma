package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.EquipamentoProjetoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoProjetoResponseDTO;
import com.br.pruma.application.service.EquipamentoProjetoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipamentos-projeto")
@RequiredArgsConstructor
public class EquipamentoProjetoController {
    private final EquipamentoProjetoService service;

    @PostMapping
    public ResponseEntity<EquipamentoProjetoResponseDTO> criar(@Valid @RequestBody EquipamentoProjetoRequestDTO dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoProjetoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EquipamentoProjetoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoProjetoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody EquipamentoProjetoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}