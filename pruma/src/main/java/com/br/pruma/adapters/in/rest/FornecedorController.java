package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.FornecedorRequestDTO;
import com.br.pruma.application.dto.response.FornecedorResponseDTO;
import com.br.pruma.application.service.FornecedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {
    private final FornecedorService service;

    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> criar(@Valid @RequestBody FornecedorRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(requestDTO));
    }
    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody FornecedorRequestDTO requestDTO) {
        return ResponseEntity.ok(service.atualizar(id, requestDTO));
    }
}