package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.MensagemInstantaneaAuxRequestDTO;
import com.br.pruma.application.dto.response.MensagemInstantaneaAuxResponseDTO;
import com.br.pruma.application.dto.update.MensagemInstantaneaAuxUpdateDTO;
import com.br.pruma.application.service.MensagemInstantaneaAuxService;
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
@RequestMapping("/pruma/v1/mensagem-instantanea-aux")
@Tag(name = "MensagemInstantaneaAux", description = "Gerencia metadados auxiliares de mensagens instantâneas")
@RequiredArgsConstructor
public class MensagemInstantaneaAuxController {

    private final MensagemInstantaneaAuxService service;

    @Operation(summary = "Cria metadados auxiliares para uma mensagem instantânea")
    @PostMapping
    public ResponseEntity<MensagemInstantaneaAuxResponseDTO> create(
            @Valid @RequestBody MensagemInstantaneaAuxRequestDTO request
    ) {
        MensagemInstantaneaAuxResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{mensagemId}")
                .buildAndExpand(response.getMensagemId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os metadados auxiliares")
    @GetMapping
    public ResponseEntity<List<MensagemInstantaneaAuxResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Consulta metadados auxiliares por ID da mensagem")
    @GetMapping("/{mensagemId}")
    public ResponseEntity<MensagemInstantaneaAuxResponseDTO> getByMensagemId(
            @PathVariable Integer mensagemId
    ) {
        return ResponseEntity.ok(service.getByMensagemId(mensagemId));
    }

    @Operation(summary = "Atualiza parcialmente os metadados auxiliares de uma mensagem")
    @PutMapping("/{mensagemId}")
    public ResponseEntity<MensagemInstantaneaAuxResponseDTO> update(
            @PathVariable Integer mensagemId,
            @Valid @RequestBody MensagemInstantaneaAuxUpdateDTO request
    ) {
        return ResponseEntity.ok(service.update(mensagemId, request));
    }

    @Operation(summary = "Exclui metadados auxiliares por ID da mensagem")
    @DeleteMapping("/{mensagemId}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer mensagemId
    ) {
        service.delete(mensagemId);
        return ResponseEntity.noContent().build();
    }
}