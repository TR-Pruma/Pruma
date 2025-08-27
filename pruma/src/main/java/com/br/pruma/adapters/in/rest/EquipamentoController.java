package com.br.pruma.adapters.in.rest;


import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.update.EquipamentoAtivoUpdateDTO;
import com.br.pruma.application.dto.response.EquipamentoListDTO;
import com.br.pruma.application.dto.response.EquipamentoResponseDTO;
import com.br.pruma.application.dto.update.EquipamentoStatusUpdateDTO;
import com.br.pruma.application.service.EquipamentoService;
import com.br.pruma.core.enums.StatusEquipamento;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> atualizar(@PathVariable Integer id,
                                                            @Valid @RequestBody EquipamentoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<EquipamentoListDTO>> listar(@RequestParam(required = false) String nome,
                                                           @RequestParam(required = false) StatusEquipamento status,
                                                           @RequestParam(required = false) Boolean ativo,
                                                           Pageable pageable) {
        return ResponseEntity.ok(service.listar(nome, status, ativo, pageable));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Integer id,
                                                @Valid @RequestBody EquipamentoStatusUpdateDTO dto) {
        service.atualizarStatus(id, dto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/ativo")
    public ResponseEntity<Void> atualizarAtivo(@PathVariable Integer id,
                                               @Valid @RequestBody EquipamentoAtivoUpdateDTO dto) {
        service.atualizarAtivo(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/inativar-lote")
    public ResponseEntity<Void> inativarEmLote(@RequestBody List<Integer> ids) {
        service.inativarEmLote(ids);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reativar-lote")
    public ResponseEntity<Void> reativarEmLote(@RequestBody List<Integer> ids) {
        service.reativarEmLote(ids);
        return ResponseEntity.noContent().build();
    }
}