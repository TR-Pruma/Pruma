package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.RelatorioRequestDTO;
import com.br.pruma.application.dto.response.RelatorioResponseDTO;
import com.br.pruma.application.dto.update.RelatorioUpdateDTO;
import com.br.pruma.application.service.RelatorioService;
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

@Tag(name = "Relatorio", description = "Operações relacionadas a relatórios")
@RestController
@RequestMapping("/pruma/v1/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService service;

    @PostMapping
    public ResponseEntity<RelatorioResponseDTO> criar(@RequestBody @Valid RelatorioRequestDTO request) {
        RelatorioResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelatorioResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<RelatorioResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/paginado")
    public ResponseEntity<Page<RelatorioResponseDTO>> listarPaginado(Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RelatorioResponseDTO> atualizar(@PathVariable Integer id,
                                                          @RequestBody @Valid RelatorioUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RelatorioResponseDTO> substituir(@PathVariable Integer id,
                                                           @RequestBody @Valid RelatorioRequestDTO dto) {
        return ResponseEntity.ok(service.replace(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
