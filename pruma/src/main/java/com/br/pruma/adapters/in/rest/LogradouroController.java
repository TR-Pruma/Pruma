package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.LogradouroRequestDTO;
import com.br.pruma.application.dto.response.LogradouroResponseDTO;
import com.br.pruma.application.dto.update.LogradouroUpdateDTO;
import com.br.pruma.application.service.LogradouroService;
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
@RequestMapping("/pruma/v1/logradouro")
@Tag(name = "Logradouro", description = "Gerencia logradouros")
@RequiredArgsConstructor
public class LogradouroController {

    private final LogradouroService service;

    @Operation(summary = "Cria um novo logradouro")
    @PostMapping
    public ResponseEntity<LogradouroResponseDTO> create(
            @Valid @RequestBody LogradouroRequestDTO request
    ) {
        LogradouroResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os logradouros")
    @GetMapping
    public ResponseEntity<List<LogradouroResponseDTO>> listAll() {
        List<LogradouroResponseDTO> list = service.listAll();
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Busca um logradouro por ID")
    @GetMapping("/{id}")
    public ResponseEntity<LogradouroResponseDTO> getById(
            @PathVariable Integer id
    ) {
        LogradouroResponseDTO response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza um logradouro existente")
    @PutMapping("/{id}")
    public ResponseEntity<LogradouroResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody LogradouroUpdateDTO request
    ) {
        LogradouroResponseDTO response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove um logradouro por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}