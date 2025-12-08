package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.EnderecoRequestDTO;
import com.br.pruma.application.dto.response.EnderecoResponseDTO;
import com.br.pruma.core.repository.EnderecoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

    private final EnderecoService service;

    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> criar(@Valid @RequestBody EnderecoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @GetMapping
    public ResponseEntity<List<EnderecoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody EnderecoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}