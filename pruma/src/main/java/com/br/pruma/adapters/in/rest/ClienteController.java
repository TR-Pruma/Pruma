package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.application.dto.update.ClienteUpdateDTO;
import com.br.pruma.application.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Cliente", description = "Operações relacionadas a clientes")
@RestController
@RequestMapping("/pruma/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> criar(@RequestBody @Valid ClienteRequestDTO request) {
        ClienteResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<ClienteResponseDTO>> listarPaginado(Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Integer id,
                                                        @RequestBody @Valid ClienteUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> substituir(@PathVariable Integer id,
                                                         @RequestBody @Valid ClienteRequestDTO dto) {
        return ResponseEntity.ok(service.replace(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
