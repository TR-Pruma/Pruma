package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ClienteTipoRequestDTO;
import com.br.pruma.application.dto.response.ClienteTipoResponseDTO;
import com.br.pruma.application.service.ClienteTipoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente-tipos")
@RequiredArgsConstructor
public class ClienteTipoController {

    private final ClienteTipoService clienteTipoService;

    @PostMapping
    public ResponseEntity<ClienteTipoResponseDTO> criar(@Valid @RequestBody ClienteTipoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteTipoService.criar(request));
    }

    @GetMapping
    public ResponseEntity<List<ClienteTipoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(clienteTipoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteTipoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(clienteTipoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteTipoResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ClienteTipoRequestDTO request) {
        return ResponseEntity.ok(clienteTipoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        clienteTipoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
