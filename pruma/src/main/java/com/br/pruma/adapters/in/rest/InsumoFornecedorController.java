package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.InsumoFornecedorRequestDTO;
import com.br.pruma.application.dto.response.InsumoFornecedorResponseDTO;
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

@RestController
@RequestMapping("/pruma/v1/insumos/{insumoId}/fornecedores")
@Tag(name = "InsumoFornecedor", description = "Gerencia associações entre insumos e fornecedores")
@RequiredArgsConstructor
public class InsumoFornecedorController {

    private final InsumoFornecedorService service;

    @Operation(summary = "Cria uma associação Insumo–Fornecedor")
    @PostMapping
    public ResponseEntity<InsumoFornecedorResponseDTO> create(
            @PathVariable Integer insumoId,
            @Valid @RequestBody InsumoFornecedorRequestDTO request
    ) {
        // Garante que o insumoId da URL seja aplicado no DTO
        var dto = new InsumoFornecedorRequestDTO(
                insumoId,
                request.getFornecedorId(),
                request.getPreco()
        );
        InsumoFornecedorResponseDTO response = service.create(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{fornecedorId}")
                .buildAndExpand(response.getFornecedorId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
    @Operation(summary = "Lista todos os fornecedores de um insumo")
    @GetMapping
    public ResponseEntity<List<InsumoFornecedorResponseDTO>> findAll(
            @PathVariable Integer insumoId
    ) {
        List<InsumoFornecedorResponseDTO> list = service.findAllByInsumo(insumoId);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Busca uma associação Insumo–Fornecedor específica")
    @GetMapping("/{fornecedorId}")
    public ResponseEntity<InsumoFornecedorResponseDTO> findById(
            @PathVariable Integer insumoId,
            @PathVariable Integer fornecedorId
    ) {
        InsumoFornecedorResponseDTO response = service.findById(insumoId, fornecedorId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualiza o preço negociado de um Insumo–Fornecedor")
    @PutMapping("/{fornecedorId}")
    public ResponseEntity<InsumoFornecedorResponseDTO> update(
            @PathVariable Integer insumoId,
            @PathVariable Integer fornecedorId,
            @Valid @RequestBody InsumoFornecedorRequestDTO request
    ) {
        var dto = new InsumoFornecedorRequestDTO(
                insumoId,
                fornecedorId,
                request.getPreco()
        );
        InsumoFornecedorResponseDTO response = service.update(insumoId, fornecedorId, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove a associação Insumo–Fornecedor")
    @DeleteMapping("/{fornecedorId}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer insumoId,
            @PathVariable Integer fornecedorId
    ) {
        service.delete(insumoId, fornecedorId);
        return ResponseEntity.noContent().build();
    }



}
