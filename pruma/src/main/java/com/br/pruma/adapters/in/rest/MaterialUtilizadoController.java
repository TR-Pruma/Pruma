package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.MaterialUtilizadoRequestDTO;
import com.br.pruma.application.dto.response.MaterialUtilizadoResponseDTO;
import com.br.pruma.application.service.MaterialUtilizadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "MaterialUtilizado", description = "Operações relacionadas a materiais utilizados")
@RestController
@RequestMapping("/pruma/v1/materiais-utilizados")
@RequiredArgsConstructor
public class MaterialUtilizadoController {

    private final MaterialUtilizadoService service;

    @Operation(summary = "Lista todos")
    @GetMapping
    public ResponseEntity<List<MaterialUtilizadoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista por projeto")
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<MaterialUtilizadoResponseDTO>> listarPorProjeto(@PathVariable Integer projetoId) {
        return ResponseEntity.ok(service.listByProjeto(projetoId));
    }

    @Operation(summary = "Busca por ID")
    @GetMapping("/{id}")
    public ResponseEntity<MaterialUtilizadoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria novo")
    @PostMapping
    public ResponseEntity<MaterialUtilizadoResponseDTO> criar(@RequestBody @Valid MaterialUtilizadoRequestDTO dto) {
        MaterialUtilizadoResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.id()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza por ID")
    @PutMapping("/{id}")
    public ResponseEntity<MaterialUtilizadoResponseDTO> atualizar(@PathVariable Integer id,
                                                                   @RequestBody @Valid MaterialUtilizadoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
