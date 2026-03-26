package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.HistoricoLocalizacaoRequestDTO;
import com.br.pruma.application.dto.response.HistoricoLocalizacaoResponseDTO;
import com.br.pruma.application.service.HistoricoLocalizacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historicos-localizacao")
@RequiredArgsConstructor
public class HistoricoLocalizacaoController {

    private final HistoricoLocalizacaoService service;

    @PostMapping
    public ResponseEntity<HistoricoLocalizacaoResponseDTO> criar(@RequestBody HistoricoLocalizacaoRequestDTO dto) {
        return ResponseEntity.ok(service.salvar(dto));
    }
    @GetMapping
    public ResponseEntity<List<HistoricoLocalizacaoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<HistoricoLocalizacaoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}