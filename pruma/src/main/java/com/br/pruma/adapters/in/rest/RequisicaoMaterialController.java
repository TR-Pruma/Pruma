package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.RequisicaoMaterialRequestDTO;
import com.br.pruma.application.dto.response.RequisicaoMaterialResponseDTO;
import com.br.pruma.application.dto.update.RequisicaoMaterialUpdateDTO;
import com.br.pruma.application.service.RequisicaoMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "RequisicaoMaterial", description = "Operações relacionadas a requisições de material")
@RestController
@RequestMapping("/pruma/v1/requisicoes-material")
@RequiredArgsConstructor
public class RequisicaoMaterialController {

    private final RequisicaoMaterialService service;

    @Operation(summary = "Lista todas as requisições de material")
    @GetMapping
    public ResponseEntity<List<RequisicaoMaterialResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Lista requisições de material por projeto")
    @GetMapping("/projeto/{projetoId}")
    public ResponseEntity<List<RequisicaoMaterialResponseDTO>> listarPorProjeto(@PathVariable Integer projetoId) {
        return ResponseEntity.ok(service.listByProjeto(projetoId));
    }

    @Operation(summary = "Busca requisição de material por ID")
    @GetMapping("/{id}")
    public ResponseEntity<RequisicaoMaterialResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria nova requisição de material")
    @PostMapping
    public ResponseEntity<RequisicaoMaterialResponseDTO> criar(@RequestBody @Valid RequisicaoMaterialRequestDTO dto) {
        RequisicaoMaterialResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.id()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza requisição de material por ID")
    @PatchMapping("/{id}")
    public ResponseEntity<RequisicaoMaterialResponseDTO> atualizar(@PathVariable Integer id,
                                                                   @RequestBody @Valid RequisicaoMaterialUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta requisição de material por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
