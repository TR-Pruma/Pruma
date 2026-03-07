package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.StatusSolicitacaoRequestDTO;
import com.br.pruma.application.dto.response.StatusSolicitacaoResponseDTO;
import com.br.pruma.application.dto.update.StatusSolicitacaoUpdateDTO;
import com.br.pruma.application.service.StatusSolicitacaoService;
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
@RequestMapping("/pruma/v1/status-solicitacoes")
@Tag(name = "StatusSolicitacao", description = "Gerencia status de solicitação")
@RequiredArgsConstructor
public class StatusSolicitacaoController {

    private final StatusSolicitacaoService service;

    @Operation(summary = "Cria um novo status de solicitação")
    @PostMapping
    public ResponseEntity<StatusSolicitacaoResponseDTO> create(@Valid @RequestBody StatusSolicitacaoRequestDTO request) {
        StatusSolicitacaoResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os status de solicitação")
    @GetMapping
    public ResponseEntity<List<StatusSolicitacaoResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Obtém um status de solicitação por ID")
    @GetMapping("/{id}")
    public ResponseEntity<StatusSolicitacaoResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Atualiza parcialmente um status de solicitação")
    @PutMapping("/{id}")
    public ResponseEntity<StatusSolicitacaoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody StatusSolicitacaoUpdateDTO request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Exclui permanentemente um status de solicitação")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
