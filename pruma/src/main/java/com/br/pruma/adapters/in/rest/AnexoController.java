package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.AnexoRequestDTO;
import com.br.pruma.application.dto.response.AnexoResponseDTO;
import com.br.pruma.application.service.AnexoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Tag(name = "Anexo", description = "Operações relacionadas a anexos")
@RestController
@RequestMapping("pruma/v1/anexos")
public class AnexoController {
    private static final Logger log = LoggerFactory.getLogger(AnexoController.class);
    private final AnexoService service;

    public AnexoController(AnexoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AnexoResponseDTO>> listarTodos() {
        log.info("Listando todos os anexos");
        List<AnexoResponseDTO> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnexoResponseDTO> buscarPorId(@PathVariable Long id) {
        log.info("Buscando anexo por id: {}", id);
        Optional<AnexoResponseDTO> dtoOpt = Optional.ofNullable(service.buscarPorId(id));
        return dtoOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AnexoResponseDTO> criar(@RequestBody @Valid AnexoRequestDTO dto) {
        log.info("Criando novo anexo");
        AnexoResponseDTO salvo = service.salvar(dto);
        return ResponseEntity.created(URI.create("/pruma/v1/anexos/" + salvo.getId())).body(salvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Deletando anexo com id: {}", id);
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
