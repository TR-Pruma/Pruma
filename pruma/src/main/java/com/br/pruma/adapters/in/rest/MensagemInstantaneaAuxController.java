package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.MensagemInstantaneaAuxRequestDTO;
import com.br.pruma.application.dto.response.MensagemInstantaneaAuxResponseDTO;
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

@Tag(name = "MensagemInstantaneaAux", description = "Operações auxiliares de mensagem instantânea")
@RestController
@RequestMapping("/pruma/v1/mensagens-instantaneas-aux")
@RequiredArgsConstructor
public class MensagemInstantaneaAuxController {

    private final MensagemInstantaneaAuxService service;

    @Operation(summary = "Lista todos os auxiliares de mensagem instantânea")
    @GetMapping
    public ResponseEntity<List<MensagemInstantaneaAuxResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca auxiliar de mensagem instantânea por ID")
    @GetMapping("/{id}")
    public ResponseEntity<MensagemInstantaneaAuxResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria novo auxiliar de mensagem instantânea")
    @PostMapping
    public ResponseEntity<MensagemInstantaneaAuxResponseDTO> criar(@RequestBody @Valid MensagemInstantaneaAuxRequestDTO dto) {
        MensagemInstantaneaAuxResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Deleta auxiliar de mensagem instantânea por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
