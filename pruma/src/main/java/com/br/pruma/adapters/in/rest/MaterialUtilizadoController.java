package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.MaterialUtilizadoRequestDTO;
import com.br.pruma.application.dto.response.MaterialUtilizadoResponseDTO;
import com.br.pruma.application.dto.update.MaterialUtilizadoUpdateDTO;
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

    @Operation(summary = "Lista todos os materiais utilizados")
    @GetMapping
    public ResponseEntity<List<MaterialUtilizadoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista materiais utilizados por obra")
    @GetMapping("/obra/{obraId}")
    public ResponseEntity<List<MaterialUtilizadoResponseDTO>> listarPorObra(@PathVariable Integer obraId) {
        return ResponseEntity.ok(service.listByProjeto(obraId));
    }

    @Operation(summary = "Busca material utilizado por ID")
    @GetMapping("/{id}")
    public ResponseEntity<MaterialUtilizadoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria novo material utilizado")
    @PostMapping
    public ResponseEntity<MaterialUtilizadoResponseDTO> criar(@RequestBody @Valid MaterialUtilizadoRequestDTO dto) {
        MaterialUtilizadoResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza material utilizado por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<MaterialUtilizadoResponseDTO> atualizar(@PathVariable Integer id,
                                                                   @RequestBody @Valid MaterialUtilizadoUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta material utilizado por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
