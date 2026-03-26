package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.FaseCronogramaRequestDTO;
import com.br.pruma.application.dto.response.FaseCronogramaResponseDTO;
import com.br.pruma.application.service.FaseCronogramaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fases-cronograma")
@RequiredArgsConstructor
public class FaseCronogramaController {

    private final FaseCronogramaService service;

    @PostMapping
    public ResponseEntity<FaseCronogramaResponseDTO> criar(@Valid @RequestBody FaseCronogramaRequestDTO dto) {
        return ResponseEntity.ok(service.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaseCronogramaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @GetMapping
    public ResponseEntity<List<FaseCronogramaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
    @PutMapping("/{id}")
    public ResponseEntity<FaseCronogramaResponseDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody FaseCronogramaRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
