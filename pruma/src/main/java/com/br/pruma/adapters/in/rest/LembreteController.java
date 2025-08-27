package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.LembreteRequestDTO;
import com.br.pruma.application.dto.response.LembreteResponseDTO;
import com.br.pruma.application.dto.update.LembreteUpdateDTO;
import com.br.pruma.application.service.LembreteService;
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
@RequestMapping("/pruma/v1/lembretes")
@Tag(name = "Lembrete", description = "Gerencia lembretes de clientes")
@RequiredArgsConstructor
public class LembreteController {

    private final LembreteService service;

    @Operation(summary = "Cria um novo lembrete")
    @PostMapping
    public ResponseEntity<LembreteResponseDTO> create(
            @Valid @RequestBody LembreteRequestDTO request
    ) {
        LembreteResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os lembretes")
    @GetMapping
    public ResponseEntity<List<LembreteResponseDTO>> listAll() {
        List<LembreteResponseDTO> lembretes = service.listAll();
        return ResponseEntity.ok(lembretes);
    }

    @Operation(summary = "Busca um lembrete por ID")
    @GetMapping("/{id}")
    public ResponseEntity<LembreteResponseDTO> getById(
            @PathVariable Integer id
    ) {
        LembreteResponseDTO response = service.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Lista lembretes por cliente (CPF)")
    @GetMapping("/clientes/{clienteCpf}")
    public ResponseEntity<List<LembreteResponseDTO>> listByCliente(
            @PathVariable String clienteCpf
    ) {
        List<LembreteResponseDTO> lembretes = service.listByCliente(clienteCpf);
        return ResponseEntity.ok(lembretes);
    }

    @Operation(summary = "Lista lembretes por tipo de usuário")
    @GetMapping("/tipo-usuarios/{tipoUsuarioId}")
    public ResponseEntity<List<LembreteResponseDTO>> listByTipoUsuario(
            @PathVariable Integer tipoUsuarioId
    ) {
        List<LembreteResponseDTO> lembretes = service.listByTipoUsuario(tipoUsuarioId);
        return ResponseEntity.ok(lembretes);
    }

    @Operation(summary = "Atualiza parcialmente um lembrete existente")
    @PutMapping("/{id}")
    public ResponseEntity<LembreteResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody LembreteUpdateDTO request
    ) {
        LembreteResponseDTO response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove um lembrete")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}