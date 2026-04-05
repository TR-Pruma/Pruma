package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.InsumoFornecedorRequestDTO;
import com.br.pruma.application.dto.response.InsumoFornecedorResponseDTO;
import com.br.pruma.application.dto.update.InsumoFornecedorUpdateDTO;
import com.br.pruma.application.service.InsumoFornecedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "InsumoFornecedor", description = "Operações relacionadas a insumos de fornecedores")
@RestController
@RequestMapping("/pruma/v1/insumos-fornecedor")
@RequiredArgsConstructor
public class InsumoFornecedorController {

    private final InsumoFornecedorService service;

    @Operation(summary = "Lista todos os insumos-fornecedor")
    @GetMapping
    public ResponseEntity<List<InsumoFornecedorResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista insumos-fornecedor por fornecedor")
    @GetMapping("/fornecedor/{fornecedorId}")
    public ResponseEntity<List<InsumoFornecedorResponseDTO>> listarPorFornecedor(@PathVariable Integer fornecedorId) {
        return ResponseEntity.ok(service.listByFornecedor(fornecedorId));
    }

    @Operation(summary = "Lista insumos-fornecedor por insumo")
    @GetMapping("/insumo/{insumoId}")
    public ResponseEntity<List<InsumoFornecedorResponseDTO>> listarPorInsumo(@PathVariable Integer insumoId) {
        return ResponseEntity.ok(service.listByInsumo(insumoId));
    }

    @Operation(summary = "Busca insumo-fornecedor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<InsumoFornecedorResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria novo insumo-fornecedor")
    @PostMapping
    public ResponseEntity<InsumoFornecedorResponseDTO> criar(@RequestBody @Valid InsumoFornecedorRequestDTO dto) {
        InsumoFornecedorResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.getId()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza insumo-fornecedor por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<InsumoFornecedorResponseDTO> atualizar(@PathVariable Integer id,
                                                                 @RequestBody @Valid InsumoFornecedorUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta insumo-fornecedor por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
