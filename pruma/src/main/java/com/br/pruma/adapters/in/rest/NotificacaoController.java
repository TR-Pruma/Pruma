package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.NotificacaoRequestDTO;
import com.br.pruma.application.dto.response.NotificacaoResponseDTO;
import com.br.pruma.application.dto.update.NotificacaoUpdateDTO;
import com.br.pruma.application.service.NotificacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Notificacao", description = "Operações relacionadas a notificações")
@RestController
@RequestMapping("/pruma/v1/notificacoes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final NotificacaoService service;

    @Operation(summary = "Lista todas as notificações")
    @GetMapping
    public ResponseEntity<List<NotificacaoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista notificações por usuário")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacaoResponseDTO>> listarPorUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.listByUsuario(usuarioId));
    }

    @Operation(summary = "Busca notificação por ID")
    @GetMapping("/{id}")
    public ResponseEntity<NotificacaoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria nova notificação")
    @PostMapping
    public ResponseEntity<NotificacaoResponseDTO> criar(@RequestBody @Valid NotificacaoRequestDTO dto) {
        NotificacaoResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza notificação por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<NotificacaoResponseDTO> atualizar(@PathVariable Integer id,
                                                            @RequestBody @Valid NotificacaoUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta notificação por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
