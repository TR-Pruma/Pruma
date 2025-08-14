package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import com.br.pruma.application.service.CronogramaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cronogramas")
@RequiredArgsConstructor
@Validated
public class CronogramaController {

    private final CronogramaService cronogramaService;

    @PostMapping
    public ResponseEntity<CronogramaResponseDTO> create(@Valid @RequestBody CronogramaRequestDTO dto) {
        CronogramaResponseDTO created = cronogramaService.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CronogramaResponseDTO> update(@PathVariable Integer id,
                                                        @Valid @RequestBody CronogramaRequestDTO dto) {
        CronogramaResponseDTO updated = cronogramaService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CronogramaResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(cronogramaService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CronogramaResponseDTO>> list(Pageable pageable) {
        return ResponseEntity.ok(cronogramaService.list(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        cronogramaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}