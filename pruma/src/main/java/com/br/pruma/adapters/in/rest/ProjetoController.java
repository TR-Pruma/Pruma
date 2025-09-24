package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ProjetoRequestDTO;
import com.br.pruma.application.dto.response.ProjetoResponseDTO;
import com.br.pruma.application.dto.update.ProjetoUpdateDTO;
import com.br.pruma.application.service.ProjetoService;
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
@RequestMapping("/pruma/v1/projetos")
@Tag(name = "Projeto", description = "Gerencia projetos")
@RequiredArgsConstructor
public class ProjetoController {

    private final ProjetoService service;

    @Operation(summary = "Cria um novo projeto")
    @PostMapping
    public ResponseEntity<ProjetoResponseDTO> create(@Valid @RequestBody ProjetoRequestDTO request) {
        ProjetoResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os projetos")
    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista projetos com paginação")
    @GetMapping("/page")
    public ResponseEntity<?> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @Operation(summary = "Obtém um projeto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista projetos por nome (contendo, case-insensitive)")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<ProjetoResponseDTO>> listByNome(@PathVariable String nome) {
        return ResponseEntity.ok(service.listByNome(nome));
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

    @Operation(summary = "Atualiza parcialmente um projeto")
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ProjetoUpdateDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Substitui os campos permitidos de um projeto (PUT semantics)")
    @PostMapping("/{id}/replace")
    public ResponseEntity<ProjetoResponseDTO> replace(
            @PathVariable Integer id,
            @Valid @RequestBody ProjetoRequestDTO request
    ) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @Operation(summary = "Exclui permanentemente um projeto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
