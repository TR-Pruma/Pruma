package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ProjetoRequestDTO;
import com.br.pruma.application.dto.response.ProjetoResponseDTO;
import com.br.pruma.application.dto.update.ProjetoUpdateDTO;
import com.br.pruma.application.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
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

    @Operation(summary = "Lista todos os projetos (sem paginação)")
    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista projetos com paginação")
    @GetMapping("/page")
    public ResponseEntity<Page<ProjetoResponseDTO>> list(
            @PageableDefault(size = 20) Pageable pageable) {
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

    @Operation(summary = "Busca projetos por nome com paginação")
    @GetMapping("/search")
    public ResponseEntity<Page<ProjetoResponseDTO>> searchByNome(
            @RequestParam(name = "nome", required = false, defaultValue = "") String nome,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.listByNomePaginado(nome, pageable));
    }

    @Operation(summary = "Lista projetos por data de criação exata")
    @GetMapping("/data")
    public ResponseEntity<Page<ProjetoResponseDTO>> listByData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.listByDataCriacao(date, pageable));
    }

    @Operation(summary = "Lista projetos por intervalo de data de criação")
    @GetMapping("/data/intervalo")
    public ResponseEntity<Page<ProjetoResponseDTO>> listByDataIntervalo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.listByDataCriacaoBetween(inicio, fim, pageable));
    }

    @Operation(summary = "Atualiza parcialmente um projeto")
    @PatchMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ProjetoUpdateDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Substitui completamente um projeto (PUT)")
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> replace(
            @PathVariable Integer id,
            @Valid @RequestBody ProjetoRequestDTO request) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @Operation(summary = "Exclui permanentemente um projeto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
