package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ImagemProjetoRequestDTO;
import com.br.pruma.application.dto.response.ImagemProjetoResponseDTO;
import com.br.pruma.application.dto.update.ImagemProjetoUpdateDTO;
import com.br.pruma.application.service.ImagemProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "ImagemProjeto", description = "Operações relacionadas a imagens de projetos")
@RestController
@RequestMapping("/pruma/v1/imagens-projeto")
@RequiredArgsConstructor
public class ImagemProjetoController {

    private final ImagemProjetoService service;

    @Operation(summary = "Lista todas as imagens de projeto")
    @GetMapping
    public ResponseEntity<List<ImagemProjetoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista imagens de projeto por obra")
    @GetMapping("/obra/{obraId}")
    public ResponseEntity<List<ImagemProjetoResponseDTO>> listarPorObra(@PathVariable Integer obraId) {
        return ResponseEntity.ok(service.listByObra(obraId));
    }

    @Operation(summary = "Busca imagem de projeto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ImagemProjetoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria nova imagem de projeto")
    @PostMapping
    public ResponseEntity<ImagemProjetoResponseDTO> criar(@RequestBody @Valid ImagemProjetoRequestDTO dto) {
        ImagemProjetoResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.id()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza imagem de projeto por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<ImagemProjetoResponseDTO> atualizar(@PathVariable Integer id,
                                                              @RequestBody @Valid ImagemProjetoUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta imagem de projeto por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
