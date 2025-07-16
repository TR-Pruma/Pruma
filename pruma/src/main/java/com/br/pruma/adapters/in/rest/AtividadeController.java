package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.AtividadeRequestDTO;
import com.br.pruma.application.dto.response.AtividadeResponseDTO;
import com.br.pruma.application.mapper.AtividadeMapper;
import com.br.pruma.application.service.AtividadeService;
import com.br.pruma.core.domain.Atividade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pruma/v1/atividades")
@Api(tags = "Atividades")
public class AtividadeController {

    private final AtividadeService atividadeService;
    private final AtividadeMapper atividadeMapper;

    public AtividadeController(AtividadeService atividadeService, AtividadeMapper atividadeMapper) {
        this.atividadeService = atividadeService;
        this.atividadeMapper = atividadeMapper;
    }

    @GetMapping
    @ApiOperation("Listar todas as atividades")
    public ResponseEntity<List<AtividadeResponseDTO>> listarTodas() {
        List<Atividade> atividades = atividadeService.listarTodos();
        List<AtividadeResponseDTO> resposta = atividades.stream()
                .map(atividadeMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    @ApiOperation("Buscar atividade por ID")
    public ResponseEntity<AtividadeResponseDTO> buscarPorId(@PathVariable Integer id) {
        Optional<Atividade> atividade = atividadeService.buscarPorId(id);
        return atividade.map(a -> ResponseEntity.ok(atividadeMapper.toResponseDTO(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation("Criar nova atividade")
    public ResponseEntity<AtividadeResponseDTO> criar(@RequestBody AtividadeRequestDTO dto) {
        Atividade nova = atividadeMapper.toEntity(dto);
        Atividade salvo = atividadeService.salvar(nova);
        return ResponseEntity.ok(atividadeMapper.toResponseDTO(salvo));
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar atividade existente")
    public ResponseEntity<AtividadeResponseDTO> atualizar(@PathVariable Integer id, @RequestBody AtividadeRequestDTO dto) {
        if (atividadeService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Atividade atividadeAtualizada = atividadeMapper.toEntity(dto);
        atividadeAtualizada.setId(id);
        Atividade salvo = atividadeService.salvar(atividadeAtualizada);
        return ResponseEntity.ok(atividadeMapper.toResponseDTO(salvo));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletar atividade por ID")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        atividadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
