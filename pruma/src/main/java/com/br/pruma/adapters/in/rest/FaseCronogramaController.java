package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.FaseCronogramaRequestDTO;
import com.br.pruma.application.dto.response.FaseCronogramaResponseDTO;
import com.br.pruma.application.dto.update.FaseCronogramaUpdateDTO;
import com.br.pruma.application.service.FaseCronogramaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "FaseCronograma", description = "Operações relacionadas a fases de cronograma")
@RestController
@RequestMapping("/pruma/v1/fases-cronograma")
@RequiredArgsConstructor
public class FaseCronogramaController {

    private final FaseCronogramaService service;

    @Operation(summary = "Lista todas as fases de cronograma")
    @GetMapping
    public ResponseEntity<List<FaseCronogramaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca fase de cronograma por ID")
    @GetMapping("/{id}")
    public ResponseEntity<FaseCronogramaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria nova fase de cronograma")
    @PostMapping
    public ResponseEntity<FaseCronogramaResponseDTO> criar(@RequestBody @Valid FaseCronogramaRequestDTO dto) {
        FaseCronogramaResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza fase de cronograma por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<FaseCronogramaResponseDTO> atualizar(@PathVariable Integer id,
                                                               @RequestBody @Valid FaseCronogramaUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta fase de cronograma por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
