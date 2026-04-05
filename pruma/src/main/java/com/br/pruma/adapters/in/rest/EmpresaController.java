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

    @Operation(summary = "Busca empresa por CNPJ")
    @GetMapping("/{cnpj}")
    public ResponseEntity<EmpresaResponseDTO> buscarPorCnpj(@PathVariable String cnpj) {
        return ResponseEntity.ok(service.getById(cnpj));
    }

    @Operation(summary = "Cria nova empresa")
    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> criar(@RequestBody @Valid EmpresaRequestDTO dto) {
        EmpresaResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{cnpj}").buildAndExpand(salvo.cnpj()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza parcialmente empresa por CNPJ (PATCH semântico)")
    @PatchMapping("/{cnpj}")
    public ResponseEntity<EmpresaResponseDTO> atualizar(@PathVariable String cnpj,
                                                        @RequestBody @Valid EmpresaUpdateDTO dto) {
        return ResponseEntity.ok(service.update(cnpj, dto));
    }

    @Operation(summary = "Substitui completamente empresa por CNPJ (PUT semântico)")
    @PutMapping("/{cnpj}")
    public ResponseEntity<EmpresaResponseDTO> substituir(@PathVariable String cnpj,
                                                         @RequestBody @Valid EmpresaRequestDTO dto) {
        return ResponseEntity.ok(service.replace(cnpj, dto));
    }

    @Operation(summary = "Deleta empresa por CNPJ")
    @DeleteMapping("/{cnpj}")
    public ResponseEntity<Void> deletar(@PathVariable String cnpj) {
        service.delete(cnpj);
        return ResponseEntity.noContent().build();
    }
}
