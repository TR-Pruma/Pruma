package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.AuditoriaRequestDTO;
import com.br.pruma.application.dto.response.AuditoriaResponseDTO;
import com.br.pruma.application.mapper.AuditoriaMapper;
import com.br.pruma.application.service.AuditoriaService;
import com.br.pruma.core.domain.Auditoria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pruma/v1/auditorias")
@Tag(name = "Auditorias", description = "Operações de auditoria")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;
    private final AuditoriaMapper auditoriaMapper;

    public AuditoriaController(AuditoriaService auditoriaService, AuditoriaMapper auditoriaMapper) {
        this.auditoriaService = auditoriaService;
        this.auditoriaMapper = auditoriaMapper;
    }

    @GetMapping
    @Operation(summary = "Listar auditorias")
    public ResponseEntity<List<AuditoriaResponseDTO>> listarTodos() {
        List<Auditoria> entidades = auditoriaService.listarTodos();
        List<AuditoriaResponseDTO> dtos = entidades.stream()
                .map(auditoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Buscar auditoria por ID")
    public ResponseEntity<AuditoriaResponseDTO> buscarPorId(@PathVariable Integer id) {
        Optional<Auditoria> entidade = auditoriaService.buscarPorId(id);
        return entidade.map(e -> ResponseEntity.ok(auditoriaMapper.toResponseDTO(e)))
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    @Operation(summary = "Criar nova auditoria")
    public ResponseEntity<AuditoriaResponseDTO> criar(@RequestBody AuditoriaRequestDTO dto) {
        Auditoria nova = auditoriaMapper.toEntity(dto);
        Auditoria salva = auditoriaService.salvar(nova);
        return ResponseEntity.ok(auditoriaMapper.toResponseDTO(salva));
    }
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar auditoria existente")
    public ResponseEntity<AuditoriaResponseDTO> atualizar(@PathVariable Integer id, @RequestBody AuditoriaRequestDTO dto) {
        if (auditoriaService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Auditoria entidade = auditoriaMapper.toEntity(dto);
        entidade.setId(new UUID(0l,id));
        Auditoria atualizada = auditoriaService.salvar(entidade);
        return ResponseEntity.ok(auditoriaMapper.toResponseDTO(atualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar auditoria por ID")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        auditoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
