package com.br.pruma.adapters.in.rest;


import com.br.pruma.application.dto.request.ProfissionalDeBaseRequestDTO;
import com.br.pruma.application.dto.response.ProfissionalDeBaseResponseDTO;
import com.br.pruma.application.dto.update.ProfissionalDeBaseUpdateDTO;
import com.br.pruma.application.service.ProfissionalDeBaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pruma/v1/profissionais")
@Tag(name = "ProfissionalDeBase", description = "Gerencia profissionais de base")
@RequiredArgsConstructor
public class ProfissionalDeBaseController {

    private final ProfissionalDeBaseService service;

    @Operation(summary = "Cria um novo profissional")
    @PostMapping
    public ResponseEntity<ProfissionalDeBaseResponseDTO> create(
            @Valid @RequestBody ProfissionalDeBaseRequestDTO request
    ) {
        ProfissionalDeBaseResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os profissionais")
    @GetMapping
    public ResponseEntity<List<ProfissionalDeBaseResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista profissionais com paginação")
    @GetMapping("/page")
    public ResponseEntity<?> list(
            @PageableDefault(size = 20) Pageable pageable
    ) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @Operation(summary = "Busca profissional por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalDeBaseResponseDTO> getById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista profissionais por nome (contendo, case-insensitive)")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProfissionalDeBaseResponseDTO>> listByNome(
            @PathVariable String nome
    ) {
        return ResponseEntity.ok(service.listByNome(nome));
    }

    @Operation(summary = "Lista profissionais por especialidade (exata)")
    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<List<ProfissionalDeBaseResponseDTO>> listByEspecialidade(
            @PathVariable String especialidade
    ) {
        return ResponseEntity.ok(service.listByEspecialidade(especialidade));
    }

    @Operation(summary = "Busca por nome com opção de paginação")
    @GetMapping("/search")
    public ResponseEntity<?> searchByNome(
            @RequestParam(name = "nome", required = false, defaultValue = "") String nome,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        if (nome == null || nome.isBlank()) {
            return ResponseEntity.ok(service.list(pageable));
        }
        return ResponseEntity.ok(service.searchByNome(nome, pageable));
    }

    @Operation(summary = "Atualiza parcialmente um profissional")
    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalDeBaseResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ProfissionalDeBaseUpdateDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Substitui completamente os campos permitidos de um profissional")
    @PostMapping("/{id}/replace")
    public ResponseEntity<ProfissionalDeBaseResponseDTO> replace(
            @PathVariable Integer id,
            @Valid @RequestBody ProfissionalDeBaseRequestDTO request
    ) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @Operation(summary = "Exclui permanentemente um profissional")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
