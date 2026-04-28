package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.PosObraRequestDTO;
import com.br.pruma.application.dto.response.PosObraResponseDTO;
import com.br.pruma.application.dto.update.PosObraUpdateDTO;
import com.br.pruma.application.service.PosObraService;
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
@RequestMapping("/pruma/v1/pos-obras")
@Tag(name = "Pós-Obra", description = "Gerencia pós-obras")
@RequiredArgsConstructor
public class PosObraController {

    private final PosObraService service;

    @Operation(summary = "Cria uma nova pós-obra")
    @PostMapping
    public ResponseEntity<PosObraResponseDTO> create(@Valid @RequestBody PosObraRequestDTO request) {
        PosObraResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todas as pós-obras")
    @GetMapping
    public ResponseEntity<List<PosObraResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca pós-obra por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PosObraResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista pós-obras com paginação")
    @GetMapping("/page")
    public ResponseEntity<Page<PosObraResponseDTO>> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @Operation(summary = "Atualiza parcialmente uma pós-obra")
    @PatchMapping("/{id}")
    public ResponseEntity<PosObraResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody PosObraUpdateDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Substitui completamente uma pós-obra (PUT)")
    @PutMapping("/{id}")
    public ResponseEntity<PosObraResponseDTO> replace(
            @PathVariable Integer id,
            @Valid @RequestBody PosObraRequestDTO request) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @Operation(summary = "Exclui permanentemente uma pós-obra")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
