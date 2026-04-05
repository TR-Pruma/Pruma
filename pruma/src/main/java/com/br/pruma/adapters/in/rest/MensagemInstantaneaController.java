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

@Tag(name = "MensagemInstantanea", description = "Operações relacionadas a mensagens instantâneas")
@RestController
@RequestMapping("/pruma/v1/mensagens-instantaneas")
@RequiredArgsConstructor
public class MensagemInstantaneaController {

    private final MensagemInstantaneaService service;

    @Operation(summary = "Lista todas as mensagens instantâneas")
    @GetMapping
    public ResponseEntity<List<MensagemInstantaneaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista mensagens instantâneas por remetente")
    @GetMapping("/remetente/{remetenteId}")
    public ResponseEntity<List<MensagemInstantaneaResponseDTO>> listarPorRemetente(@PathVariable Integer remetenteId) {
        return ResponseEntity.ok(service.listByRemetente(remetenteId));
    }

    @Operation(summary = "Busca mensagem instantânea por ID")
    @GetMapping("/{id}")
    public ResponseEntity<MensagemInstantaneaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria nova mensagem instantânea")
    @PostMapping
    public ResponseEntity<MensagemInstantaneaResponseDTO> criar(@RequestBody @Valid MensagemInstantaneaRequestDTO dto) {
        MensagemInstantaneaResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza mensagem instantânea por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<MensagemInstantaneaResponseDTO> atualizar(@PathVariable Integer id,
                                                                    @RequestBody @Valid MensagemInstantaneaUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta mensagem instantânea por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
