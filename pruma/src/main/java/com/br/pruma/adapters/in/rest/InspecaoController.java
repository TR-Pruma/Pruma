package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.InspecaoRequestDTO;
import com.br.pruma.application.dto.response.InspecaoResponseDTO;
import com.br.pruma.application.service.InspecaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pruma/v1/inspecoes")
@RequiredArgsConstructor
public class InspecaoController {

    private final InspecaoService service;

    @GetMapping
    public ResponseEntity<List<InspecaoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InspecaoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<InspecaoResponseDTO> criar(
            @Valid @RequestBody InspecaoRequestDTO dto) {

        InspecaoResponseDTO criada = service.criar(dto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criada.id())
                .toUri();

        return ResponseEntity.created(location).body(criada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InspecaoResponseDTO> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody InspecaoRequestDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}