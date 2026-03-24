package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ImagemProjetoRequestDTO;
import com.br.pruma.application.dto.response.ImagemProjetoResponseDTO;
import com.br.pruma.application.service.ImagemProjetoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pruma/v1/imagens-projeto")
@RequiredArgsConstructor
public class ImagemProjetoController {

    private final ImagemProjetoService service;

    @GetMapping
    public ResponseEntity<List<ImagemProjetoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ImagemProjetoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<ImagemProjetoResponseDTO> criar(@Valid @RequestBody ImagemProjetoRequestDTO dto) {
        return ResponseEntity.ok(service.criar(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ImagemProjetoResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ImagemProjetoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
