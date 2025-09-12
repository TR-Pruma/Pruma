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

@RestController
@RequestMapping("/pruma/v1/notificacoes")
@Tag(name = "Notificacao", description = "Gerencia notificações para clientes")
@RequiredArgsConstructor
public class NotificacaoController {

    private final NotificacaoService service;

    @Operation(summary = "Cria uma nova notificação")
    @PostMapping
    public ResponseEntity<NotificacaoResponseDTO> create(
            @Valid @RequestBody NotificacaoRequestDTO request
    ) {
        NotificacaoResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todas as notificações")
    @GetMapping
    public ResponseEntity<List<NotificacaoResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca notificação por ID")
    @GetMapping("/{id}")
    public ResponseEntity<NotificacaoResponseDTO> getById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista notificações por cliente")
    @GetMapping("/cliente/{clienteCpf}")
    public ResponseEntity<List<NotificacaoResponseDTO>> listByCliente(
            @PathVariable Long clienteCpf
    ) {
        return ResponseEntity.ok(service.listByCliente(clienteCpf));
    }

    @Operation(summary = "Lista notificações por tipo de usuário")
    @GetMapping("/tipo-usuario/{tipoUsuarioId}")
    public ResponseEntity<List<NotificacaoResponseDTO>> listByTipoUsuario(
            @PathVariable Integer tipoUsuarioId
    ) {
        return ResponseEntity.ok(service.listByTipoUsuario(tipoUsuarioId));
    }

    @Operation(summary = "Lista notificações por status de leitura")
    @GetMapping("/lida/{lida}")
    public ResponseEntity<List<NotificacaoResponseDTO>> listByLida(
            @PathVariable Boolean lida
    ) {
        return ResponseEntity.ok(service.listByLida(lida));
    }

    @Operation(summary = "Atualiza parcialmente uma notificação existente")
    @PutMapping("/{id}")
    public ResponseEntity<NotificacaoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody NotificacaoUpdateDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Exclui logicamente uma notificação")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}