package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoResponseDTO;
import com.br.pruma.application.dto.update.EquipamentoUpdateDTO;
import com.br.pruma.application.service.EquipamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pruma/v1/equipamentos")
@Tag(name = "Equipamento", description = "Gerencia equipamentos")
@RequiredArgsConstructor
public class EquipamentoController {

    private final EquipamentoService service;

    @Operation(summary = "Cria um novo equipamento")
    @PostMapping
    public ResponseEntity<EquipamentoResponseDTO> create(@Valid @RequestBody EquipamentoRequestDTO request) {
        EquipamentoResponseDTO response = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Lista todos os equipamentos")
    @GetMapping
    public ResponseEntity<List<EquipamentoResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca equipamento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Lista equipamentos com paginação")
    @GetMapping("/page")
    public ResponseEntity<Page<EquipamentoResponseDTO>> list(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @Operation(summary = "Atualiza parcialmente um equipamento")
    @PatchMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> update(
            @PathVariable Integer id,
            @Valid @RequestBody EquipamentoUpdateDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Substitui completamente um equipamento (PUT)")
    @PutMapping("/{id}")
    public ResponseEntity<EquipamentoResponseDTO> replace(
            @PathVariable Integer id,
            @Valid @RequestBody EquipamentoRequestDTO request) {
        return ResponseEntity.ok(service.replace(id, request));
    }

    @Operation(summary = "Exclui permanentemente um equipamento")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
