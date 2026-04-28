package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.CategoriaRequestDTO;
import com.br.pruma.application.dto.response.CategoriaResponseDTO;
import com.br.pruma.application.dto.update.CategoriaUpdateDTO;
import com.br.pruma.application.service.CategoriaService;
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
@RequestMapping("/pruma/v1/categorias")
@Tag(name = "Categoria", description = "Gerencia categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @Operation(summary = "Cria uma nova categoria")
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> create(@Valid @RequestBody CategoriaRequestDTO request) {
        CategoriaResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todas as categorias")
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca categoria por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista categorias com paginação")
    @GetMapping("/page")
    public ResponseEntity<Page<CategoriaResponseDTO>> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @Operation(summary = "Atualiza parcialmente uma categoria")
    @PatchMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaUpdateDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Substitui completamente uma categoria (PUT)")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> replace(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaRequestDTO request) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @Operation(summary = "Exclui permanentemente uma categoria")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
