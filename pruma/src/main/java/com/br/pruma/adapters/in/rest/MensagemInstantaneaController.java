package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.MensagemInstantaneaRequestDTO;
import com.br.pruma.application.dto.response.MensagemInstantaneaResponseDTO;
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

@Tag(name = "MensagemInstantanea", description = "Operações relacionadas a mensagens instantâneas")
@RestController
@RequestMapping("/pruma/v1/mensagens-instantaneas")
@RequiredArgsConstructor
public class MensagemInstantaneaController {

    private final MensagemInstantaneaService service;

    @Operation(summary = "Lista todas as mensagens")
    @GetMapping
    public ResponseEntity<List<MensagemInstantaneaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista por remetente")
    @GetMapping("/remetente/{remetenteId}")
    public ResponseEntity<List<MensagemInstantaneaResponseDTO>> listarPorRemetente(@PathVariable Integer remetenteId) {
        return ResponseEntity.ok(service.listByRemetente(remetenteId));
    }

    @Operation(summary = "Busca mensagem por ID")
    @GetMapping("/{id}")
    public ResponseEntity<MensagemInstantaneaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria nova mensagem")
    @PostMapping
    public ResponseEntity<MensagemInstantaneaResponseDTO> criar(@RequestBody @Valid MensagemInstantaneaRequestDTO dto) {
        MensagemInstantaneaResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.id()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza mensagem por ID")
    @PutMapping("/{id}")
    public ResponseEntity<MensagemInstantaneaResponseDTO> atualizar(@PathVariable Integer id,
                                                                     @RequestBody @Valid MensagemInstantaneaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta mensagem por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
