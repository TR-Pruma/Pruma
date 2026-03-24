package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ProjetoCategoriaRequestDTO;
import com.br.pruma.application.dto.response.ProjetoCategoriaResponseDTO;
import com.br.pruma.application.service.ProjetoCategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pruma/v1/projeto-categorias")
@Tag(name = "ProjetoCategoria", description = "Gerencia categorias de projeto")
@RequiredArgsConstructor
public class ProjetoCategoriaController {

    private final ProjetoCategoriaService service;

    @Operation(summary = "Cria uma nova categoria de projeto")
    @PostMapping
    public ResponseEntity<ProjetoCategoriaResponseDTO> create(
            @Valid @RequestBody ProjetoCategoriaRequestDTO request
    ) {
        ProjetoCategoriaResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id()) // se seu DTO usar getId(), troque para getId()
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todas as categorias de projeto")
    @GetMapping
    public ResponseEntity<List<ProjetoCategoriaResponseDTO>> listAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Obtém uma categoria por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoCategoriaResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Atualiza parcialmente uma categoria de projeto")
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoCategoriaResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody ProjetoCategoriaRequestDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Exclui permanentemente uma categoria de projeto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

