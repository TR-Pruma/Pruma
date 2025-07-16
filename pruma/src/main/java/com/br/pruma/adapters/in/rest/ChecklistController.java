package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.ChecklistRequestDTO;
import com.br.pruma.application.dto.response.ChecklistResponseDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.br.pruma.application.mapper.ChecklistMapper;
import com.br.pruma.application.service.ChecklistService;
import com.br.pruma.core.domain.Checklist;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pruma/v1/checklists")
@Api(tags = "Checklists")
public class ChecklistController {
    private final ChecklistService checklistService;
    private final ChecklistMapper checklistMapper;

    public ChecklistController(ChecklistService checklistService, ChecklistMapper checklistMapper) {
        this.checklistService = checklistService;
        this.checklistMapper = checklistMapper;
    }

    @GetMapping
    @ApiOperation("Listar checklists")
    public ResponseEntity<List<ChecklistResponseDTO>> listarTodos() {
        List<Checklist> lista = checklistService.listarTodos();
        List<ChecklistResponseDTO> resposta = lista.stream()
                .map(checklistMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    @ApiOperation("Buscar checklist por ID")
    public ResponseEntity<ChecklistResponseDTO> buscarPorId(@PathVariable Integer id) {
        Optional<Checklist> checklist = checklistService.buscarPorId(id);
        return checklist.map(c -> ResponseEntity.ok(checklistMapper.toResponseDTO(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation("Criar checklist")
    public ResponseEntity<ChecklistResponseDTO> criar(@RequestBody ChecklistRequestDTO dto) {
        Checklist novo = checklistMapper.toEntity(dto);
        Checklist salvo = checklistService.salvar(novo);
        return ResponseEntity.ok(checklistMapper.toResponseDTO(salvo));
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar checklist")
    public ResponseEntity<ChecklistResponseDTO> atualizar(@PathVariable Integer id, @RequestBody ChecklistRequestDTO dto) {
        if (checklistService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Checklist atualizado = checklistMapper.toEntity(dto);
        atualizado.setId(id);
        Checklist salvo = checklistService.salvar(atualizado);
        return ResponseEntity.ok(checklistMapper.toResponseDTO(salvo));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletar checklist")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        checklistService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
