package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;
import com.br.pruma.infra.repository.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService service;

    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> criar(@Valid @RequestBody EmpresaRequestDTO dto) {
        EmpresaResponseDTO response = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/{cnpj}")
    public ResponseEntity<EmpresaResponseDTO> buscar(@PathVariable String cnpj) {
        return ResponseEntity.ok(service.buscarPorCnpj(cnpj));
    }
    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }
    @DeleteMapping("/{cnpj}")
    public ResponseEntity<Void> deletar(@PathVariable String cnpj) {
        service.deletar(cnpj);
        return ResponseEntity.noContent().build();
    }

}
