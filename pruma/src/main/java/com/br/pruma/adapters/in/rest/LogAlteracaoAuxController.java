package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.LogAlteracaoAuxRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoAuxResponseDTO;
import com.br.pruma.application.dto.update.LogAlteracaoAuxUpdateDTO;
import com.br.pruma.application.service.LogAlteracaoAuxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pruma/v1/log-alteracoes/{logId}/aux")
@Tag(name = "LogAlteracaoAux", description = "Gerencia o tipo auxiliar de cada log de alteração")
@RequiredArgsConstructor
public class LogAlteracaoAuxController {

    private final LogAlteracaoAuxService service;

    @Operation(summary = "Cria ou substitui o registro auxiliar para um log de alteração")
    @PostMapping
    public ResponseEntity<LogAlteracaoAuxResponseDTO> create(
            @PathVariable Integer logId,
            @Valid @RequestBody LogAlteracaoAuxRequestDTO requestBody
    ) {
        requestBody.setLogId(logId);

        LogAlteracaoAuxResponseDTO response = service.create(requestBody);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @Operation(summary = "Consulta o registro auxiliar de um log de alteração")
    @GetMapping
    public ResponseEntity<LogAlteracaoAuxResponseDTO> getByLog(
            @PathVariable Integer logId
    ) {
        return ResponseEntity.ok(service.getById(logId));
    }

    @Operation(summary = "Atualiza o registro auxiliar de um log de alteração")
    @PutMapping
    public ResponseEntity<LogAlteracaoAuxResponseDTO> update(
            @PathVariable Integer logId,
            @Valid @RequestBody LogAlteracaoAuxUpdateDTO dto
    ) {
        return ResponseEntity.ok(service.update(logId, dto));
    }

    @Operation(summary = "Remove o registro auxiliar de um log de alteração")
    @DeleteMapping
    public ResponseEntity<Void> delete(
            @PathVariable Integer logId
    ) {
        service.delete(logId);
        return ResponseEntity.noContent().build();
    }
}
