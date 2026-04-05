package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ProjetoCategoriaRequestDTO;
import com.br.pruma.application.dto.response.ProjetoCategoriaResponseDTO;
import com.br.pruma.application.dto.update.ProjetoCategoriaUpdateDTO;
import com.br.pruma.application.service.ProjetoCategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "ProjetoCategoria", description = "Operações relacionadas a categorias de projeto")
@RestController
@RequestMapping("/pruma/v1/projetos-categorias")
@RequiredArgsConstructor
public class ProjetoCategoriaController {

    private final ProjetoCategoriaService service;

    @Operation(summary = "Lista todas as categorias de projeto")
    @GetMapping
    public ResponseEntity<List<ProjetoCategoriaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca categoria de projeto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoCategoriaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria nova categoria de projeto")
    @PostMapping
    public ResponseEntity<ProjetoCategoriaResponseDTO> criar(@RequestBody @Valid ProjetoCategoriaRequestDTO dto) {
        ProjetoCategoriaResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza categoria de projeto por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<ProjetoCategoriaResponseDTO> atualizar(@PathVariable Integer id,
                                                                 @RequestBody @Valid ProjetoCategoriaUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta categoria de projeto por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
