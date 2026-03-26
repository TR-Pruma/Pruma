package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.AtividadeRequestDTO;
import com.br.pruma.application.dto.response.AtividadeResponseDTO;
import com.br.pruma.application.mapper.AtividadeMapper;
import com.br.pruma.application.service.AtividadeService;
import com.br.pruma.core.domain.Atividade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pruma/v1/atividades")
@Tag(name = "Atividades") // ✅ OpenAPI 3 (springdoc) no lugar do Swagger 2
public class AtividadeController {

    private final AtividadeService atividadeService;
    private final AtividadeMapper atividadeMapper;

    public AtividadeController(AtividadeService atividadeService, AtividadeMapper atividadeMapper) {
        this.atividadeService = atividadeService;
        this.atividadeMapper = atividadeMapper;
    }

    @GetMapping
    @Operation(summary = "Listar todas as atividades")
    public ResponseEntity<List<AtividadeResponseDTO>> listarTodas() {
        List<AtividadeResponseDTO> resposta = atividadeService.listarTodos()
                .stream()
                .map(atividadeMapper::toResponseDTO)
                .toList(); // ✅ Java 16+ - mais limpo que Collectors.toList()
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar atividade por ID")
    public ResponseEntity<AtividadeResponseDTO> buscarPorId(@PathVariable Integer id) {
        return atividadeService.buscarPorId(id)
                .map(a -> ResponseEntity.ok(atividadeMapper.toResponseDTO(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar nova atividade")
    public ResponseEntity<AtividadeResponseDTO> criar(@Valid @RequestBody AtividadeRequestDTO dto) {
        // ✅ @Valid ativa as validações do DTO
        Atividade salvo = atividadeService.salvar(atividadeMapper.toEntity(dto));
        AtividadeResponseDTO resposta = atividadeMapper.toResponseDTO(salvo);

        // ✅ Retorna 201 Created com Location header
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.getId())
                .toUri();
        return ResponseEntity.created(location).body(resposta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar atividade existente")
    public ResponseEntity<AtividadeResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody AtividadeRequestDTO dto) {
        // ✅ Operação atômica no service - sem race condition
        return atividadeService.atualizar(id, atividadeMapper.toEntity(dto))
                .map(salvo -> ResponseEntity.ok(atividadeMapper.toResponseDTO(salvo)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar atividade por ID")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        // ✅ Verifica existência antes de deletar
        if (atividadeService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        atividadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
