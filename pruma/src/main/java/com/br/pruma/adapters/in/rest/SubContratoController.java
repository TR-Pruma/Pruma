package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.SubContratoRequestDTO;
import com.br.pruma.application.dto.response.SubContratoResponseDTO;
import com.br.pruma.application.dto.update.SubContratoUpdateDTO;
import com.br.pruma.application.service.SubContratoService;
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
@RequestMapping("/pruma/v1/subcontratos")
@Tag(name = "SubContrato", description = "Gerencia subcontratos")
@RequiredArgsConstructor
public class SubContratoController {

    private final SubContratoService service;

    @Operation(summary = "Cria um novo subcontrato")
    @PostMapping
    public ResponseEntity<SubContratoResponseDTO> create(@Valid @RequestBody SubContratoRequestDTO request) {
        SubContratoResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os subcontratos")
    @GetMapping
    public ResponseEntity<List<SubContratoResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca subcontrato por ID")
    @GetMapping("/{id}")
    public ResponseEntity<SubContratoResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista subcontratos com paginação")
    @GetMapping("/page")
    public ResponseEntity<Page<SubContratoResponseDTO>> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @Operation(summary = "Atualiza parcialmente um subcontrato")
    @PatchMapping("/{id}")
    public ResponseEntity<SubContratoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody SubContratoUpdateDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Substitui completamente um subcontrato (PUT)")
    @PutMapping("/{id}")
    public ResponseEntity<SubContratoResponseDTO> replace(
            @PathVariable Integer id,
            @Valid @RequestBody SubContratoRequestDTO request) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @Operation(summary = "Exclui permanentemente um subcontrato")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
