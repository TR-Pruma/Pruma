package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.AuditoriaRequestDTO;
import com.br.pruma.application.dto.response.AuditoriaResponseDTO;
import com.br.pruma.application.mapper.AuditoriaMapper;
import com.br.pruma.application.service.AuditoriaService;
import com.br.pruma.core.domain.Auditoria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
        List<AuditoriaResponseDTO> dtos = auditoriaService.listarTodos()
                .stream()
                .map(auditoriaMapper::toResponseDTO)
                .toList(); // ✅ Java 16+
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar auditoria por ID")
    public ResponseEntity<AuditoriaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return auditoriaService.buscarPorId(id)
                .map(e -> ResponseEntity.ok(auditoriaMapper.toResponseDTO(e)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar nova auditoria")
    public ResponseEntity<AuditoriaResponseDTO> criar(@Valid @RequestBody AuditoriaRequestDTO dto) {
        // ✅ @Valid ativo
        Auditoria salva = auditoriaService.salvar(auditoriaMapper.toEntity(dto));
        AuditoriaResponseDTO resposta = auditoriaMapper.toResponseDTO(salva);

        // ✅ Retorna 201 Created
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salva.getId())
                .toUri();
        return ResponseEntity.created(location).body(resposta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar auditoria existente")
    public ResponseEntity<AuditoriaResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody AuditoriaRequestDTO dto) {
        // ✅ Operação atômica — sem race condition e sem bug de UUID
        return auditoriaService.atualizar(id, auditoriaMapper.toEntity(dto))
                .map(salva -> ResponseEntity.ok(auditoriaMapper.toResponseDTO(salva)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar auditoria por ID")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        // ✅ Verifica existência antes de deletar
        if (auditoriaService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        auditoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
