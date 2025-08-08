package com.br.pruma.adapters.in.rest;


import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoResponseDTO;
import com.br.pruma.application.service.EquipamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipamentos")
@RequiredArgsConstructor
public class EquipamentoController {
    private final EquipamentoService service;

    @PostMapping
    public ResponseEntity<EquipamentoResponseDTO> criar(@Valid @RequestBody EquipamentoRequestDTO dto) {
        return ResponseEntity.ok(service.criar(dto));
    }
    @GetMapping
    public ResponseEntity<List<EquipamentoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody EquipamentoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
