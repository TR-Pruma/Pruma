package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.AtividadeRequestDTO;
import com.br.pruma.application.dto.response.AtividadeResponseDTO;
import com.br.pruma.application.dto.update.AtividadeUpdateDTO;
import com.br.pruma.application.service.AtividadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pruma/v1/atividades")
@Tag(name = "Atividade", description = "Gerencia atividades")
@RequiredArgsConstructor
public class AtividadeController {

    private final AtividadeService service;

    @Operation(summary = "Cria uma nova atividade")
    @PostMapping
    public ResponseEntity<AtividadeResponseDTO> create(@Valid @RequestBody AtividadeRequestDTO request) {
        AtividadeResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todas as atividades")
    @GetMapping
    public ResponseEntity<List<AtividadeResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca atividade por ID")
    @GetMapping("/{id}")
    public ResponseEntity<AtividadeResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista atividades com paginação")
    @GetMapping("/page")
    public ResponseEntity<Page<AtividadeResponseDTO>> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @Operation(summary = "Atualiza parcialmente uma atividade")
    @PatchMapping("/{id}")
    public ResponseEntity<AtividadeResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody AtividadeUpdateDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Substitui completamente uma atividade (PUT)")
    @PutMapping("/{id}")
    public ResponseEntity<AtividadeResponseDTO> replace(
            @PathVariable Integer id,
            @Valid @RequestBody AtividadeRequestDTO request) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @Operation(summary = "Exclui permanentemente uma atividade")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
