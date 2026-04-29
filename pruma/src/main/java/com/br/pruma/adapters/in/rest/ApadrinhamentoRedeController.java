package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ApadrinhamentoRedeRequestDTO;
import com.br.pruma.application.dto.response.ApadrinhamentoRedeResponseDTO;
import com.br.pruma.application.dto.update.ApadrinhamentoRedeUpdateDTO;
import com.br.pruma.application.service.ApadrinhamentoRedeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@Tag(name = "ApadrinhamentoRede", description = "Operações relacionadas ao apadrinhamento entre profissionais da rede")
@RestController
@RequestMapping("/pruma/v1/apadrinhamentos-rede")
@RequiredArgsConstructor
public class ApadrinhamentoRedeController {

    private final ApadrinhamentoRedeService service;

    @Operation(summary = "Lista todos os apadrinhamentos de rede")
    @GetMapping
    public ResponseEntity<List<ApadrinhamentoRedeResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca apadrinhamento de rede por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApadrinhamentoRedeResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista todos os apadrinhamentos de um padrinho")
    @GetMapping("/padrinho/{padrinhoId}")
    public ResponseEntity<List<ApadrinhamentoRedeResponseDTO>> listarPorPadrinho(@PathVariable Long padrinhoId) {
        return ResponseEntity.ok(service.listByPadrinho(padrinhoId));
    }

    @Operation(summary = "Lista todos os apadrinhamentos de um afilhado")
    @GetMapping("/afilhado/{afilhadoId}")
    public ResponseEntity<List<ApadrinhamentoRedeResponseDTO>> listarPorAfilhado(@PathVariable Long afilhadoId) {
        return ResponseEntity.ok(service.listByAfilhado(afilhadoId));
    }

    @Operation(summary = "Cria novo apadrinhamento de rede")
    @PostMapping
    public ResponseEntity<ApadrinhamentoRedeResponseDTO> criar(
            @RequestBody @Valid ApadrinhamentoRedeRequestDTO dto) {
        ApadrinhamentoRedeResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.id()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza apadrinhamento de rede por ID (status/dataFim)")
    @PatchMapping("/{id}")
    public ResponseEntity<ApadrinhamentoRedeResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ApadrinhamentoRedeUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta apadrinhamento de rede por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
