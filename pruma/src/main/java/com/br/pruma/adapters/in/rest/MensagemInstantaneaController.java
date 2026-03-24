package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.MensagemInstantaneaRequestDTO;
import com.br.pruma.application.dto.response.MensagemInstantaneaResponseDTO;
import com.br.pruma.application.dto.update.MensagemInstantaneaUpdateDTO;
import com.br.pruma.application.service.MensagemInstantaneaService;
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
@RequestMapping("/pruma/v1/mensagem-instantanea")
@Tag(name = "MensagemInstantanea", description = "Gerencia mensagens instantâneas")
@RequiredArgsConstructor
public class MensagemInstantaneaController {

    private final MensagemInstantaneaService service;

    @Operation(summary = "Cria uma nova mensagem instantânea")
    @PostMapping
    public ResponseEntity<MensagemInstantaneaResponseDTO> create(
            @Valid @RequestBody MensagemInstantaneaRequestDTO request
    ) {
        MensagemInstantaneaResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todas as mensagens instantâneas")
    @GetMapping
    public ResponseEntity<List<MensagemInstantaneaResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca uma mensagem instantânea por ID")
    @GetMapping("/{id}")
    public ResponseEntity<MensagemInstantaneaResponseDTO> getById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista mensagens enviadas por cliente")
    @GetMapping("/cliente/{clienteCpf}")
    public ResponseEntity<List<MensagemInstantaneaResponseDTO>> listByCliente(
            @PathVariable Long clienteCpf
    ) {
        return ResponseEntity.ok(service.listByCliente(clienteCpf));
    }

    @Operation(summary = "Lista mensagens por tipo de usuário")
    @GetMapping("/tipo-usuario/{tipoUsuarioId}")
    public ResponseEntity<List<MensagemInstantaneaResponseDTO>> listByTipoUsuario(
            @PathVariable Integer tipoUsuarioId
    ) {
        return ResponseEntity.ok(service.listByTipoUsuario(tipoUsuarioId));
    }

    @Operation(summary = "Lista mensagens por destinatário")
    @GetMapping("/destinatario/{destinatarioId}")
    public ResponseEntity<List<MensagemInstantaneaResponseDTO>> listByDestinatario(
            @PathVariable String destinatarioId
    ) {
        return ResponseEntity.ok(service.listByDestinatario(destinatarioId));
    }

    @Operation(summary = "Atualiza parcialmente uma mensagem instantânea existente")
    @PutMapping("/{id}")
    public ResponseEntity<MensagemInstantaneaResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody MensagemInstantaneaUpdateDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Exclui uma mensagem instantânea por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}