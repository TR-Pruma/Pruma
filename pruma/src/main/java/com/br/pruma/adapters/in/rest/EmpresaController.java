package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;
import com.br.pruma.application.dto.update.EmpresaUpdateDTO;
import com.br.pruma.application.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Empresa", description = "Operações relacionadas a empresas")
@RestController
@RequestMapping("/pruma/v1/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService service;

    @Operation(summary = "Lista todas as empresas")
    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca empresa por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria nova empresa")
    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> criar(@RequestBody @Valid EmpresaRequestDTO dto) {
        EmpresaResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza parcialmente empresa por ID (PATCH semântico)")
    @PatchMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> atualizar(@PathVariable Integer id,
                                                        @RequestBody @Valid EmpresaUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Substitui completamente empresa por ID (PUT semântico)")
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> substituir(@PathVariable Integer id,
                                                         @RequestBody @Valid EmpresaRequestDTO dto) {
        return ResponseEntity.ok(service.replace(id, dto));
    }

    @Operation(summary = "Deleta empresa por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
