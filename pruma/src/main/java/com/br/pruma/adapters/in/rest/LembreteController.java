package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.LembreteRequestDTO;
import com.br.pruma.application.dto.response.LembreteResponseDTO;
import com.br.pruma.application.service.LembreteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Lembrete", description = "Operações relacionadas a lembretes")
@RestController
@RequestMapping("/pruma/v1/lembretes")
@RequiredArgsConstructor
public class LembreteController {

    private final LembreteService service;

    @Operation(summary = "Lista todos os lembretes")
    @GetMapping
    public ResponseEntity<List<LembreteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista lembretes por projeto")
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<LembreteResponseDTO>> listarPorProjeto(@PathVariable Integer projetoId) {
        return ResponseEntity.ok(service.listByProjeto(projetoId));
    }

    @Operation(summary = "Busca lembrete por ID")
    @GetMapping("/{id}")
    public ResponseEntity<LembreteResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria novo lembrete")
    @PostMapping
    public ResponseEntity<LembreteResponseDTO> criar(@RequestBody @Valid LembreteRequestDTO dto) {
        LembreteResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.id()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza lembrete por ID")
    @PutMapping("/{id}")
    public ResponseEntity<LembreteResponseDTO> atualizar(@PathVariable Integer id,
                                                         @RequestBody @Valid LembreteRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta lembrete por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
