package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.InspecaoRequestDTO;
import com.br.pruma.application.dto.response.InspecaoResponseDTO;
import com.br.pruma.application.dto.update.InspecaoUpdateDTO;
import com.br.pruma.application.service.InspecaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Inspecao", description = "Operações relacionadas a inspeções")
@RestController
@RequestMapping("/pruma/v1/inspecoes")
@RequiredArgsConstructor
public class InspecaoController {

    private final InspecaoService service;

    @Operation(summary = "Lista todas as inspeções")
    @GetMapping
    public ResponseEntity<List<InspecaoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista inspeções por projeto")
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<InspecaoResponseDTO>> listarPorProjeto(@PathVariable Integer projetoId) {
        return ResponseEntity.ok(service.listByProjeto(projetoId));
    }

    @Operation(summary = "Busca inspeção por ID")
    @GetMapping("/{id}")
    public ResponseEntity<InspecaoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria nova inspeção")
    @PostMapping
    public ResponseEntity<InspecaoResponseDTO> criar(@RequestBody @Valid InspecaoRequestDTO dto) {
        InspecaoResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.id()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza inspeção por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<InspecaoResponseDTO> atualizar(@PathVariable Integer id,
                                                         @RequestBody @Valid InspecaoUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta inspeção por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
