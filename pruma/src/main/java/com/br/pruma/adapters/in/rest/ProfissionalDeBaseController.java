package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ProfissionalDeBaseRequestDTO;
import com.br.pruma.application.dto.response.ProfissionalDeBaseResponseDTO;
import com.br.pruma.application.dto.update.ProfissionalDeBaseUpdateDTO;
import com.br.pruma.application.service.ProfissionalDeBaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "ProfissionalDeBase", description = "Operações relacionadas a profissionais de base")
@RestController
@RequestMapping("/pruma/v1/profissionais-base")
@RequiredArgsConstructor
public class ProfissionalDeBaseController {

    private final ProfissionalDeBaseService service;

    @Operation(summary = "Lista todos os profissionais de base")
    @GetMapping
    public ResponseEntity<List<ProfissionalDeBaseResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca profissional de base por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalDeBaseResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria novo profissional de base")
    @PostMapping
    public ResponseEntity<ProfissionalDeBaseResponseDTO> criar(@RequestBody @Valid ProfissionalDeBaseRequestDTO dto) {
        ProfissionalDeBaseResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza profissional de base por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<ProfissionalDeBaseResponseDTO> atualizar(@PathVariable Integer id,
                                                                   @RequestBody @Valid ProfissionalDeBaseUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta profissional de base por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
