package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ProjetoRequestDTO;
import com.br.pruma.application.dto.response.ProjetoResponseDTO;
import com.br.pruma.application.service.ProjetoService;
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

@Tag(name = "Projeto", description = "Operações relacionadas a projetos")
@RestController
@RequestMapping("/pruma/v1/projetos")
@RequiredArgsConstructor
public class ProjetoController {

    private final ProjetoService service;

    @Operation(summary = "Lista todos os projetos")
    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista projetos paginados")
    @GetMapping("/paginado")
    public ResponseEntity<Page<ProjetoResponseDTO>> listarPaginado(Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @Operation(summary = "Lista projetos por cliente")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ProjetoResponseDTO>> listarPorCliente(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(service.listByCliente(clienteId));
    }

    @Operation(summary = "Busca projeto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria novo projeto")
    @PostMapping
    public ResponseEntity<ProjetoResponseDTO> criar(@RequestBody @Valid ProjetoRequestDTO dto) {
        ProjetoResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.id()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza projeto por ID")
    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> atualizar(@PathVariable Integer id,
                                                        @RequestBody @Valid ProjetoRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta projeto por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
