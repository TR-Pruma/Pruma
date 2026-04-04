package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.AnexoRequestDTO;
import com.br.pruma.application.dto.response.AnexoResponseDTO;
import com.br.pruma.application.service.AnexoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Anexo", description = "Operações relacionadas a anexos")
@RestController
@RequestMapping("/pruma/v1/anexos")
@RequiredArgsConstructor
public class AnexoController {

    private final AnexoService service;

    @Operation(summary = "Lista todos os anexos")
    @GetMapping
    public ResponseEntity<List<AnexoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Busca anexo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<AnexoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Cria novo anexo")
    @PostMapping
    public ResponseEntity<AnexoResponseDTO> criar(@RequestBody @Valid AnexoRequestDTO dto) {
        AnexoResponseDTO salvo = service.salvar(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.id()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Deleta anexo por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
