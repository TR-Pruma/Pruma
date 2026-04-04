package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.FeedbackRequestDTO;
import com.br.pruma.application.dto.response.FeedbackResponseDTO;
import com.br.pruma.application.service.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Feedback", description = "Operações relacionadas a feedbacks")
@RestController
@RequestMapping("/pruma/v1/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService service;

    @Operation(summary = "Lista todos os feedbacks")
    @GetMapping
    public ResponseEntity<List<FeedbackResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listAll());
    }

    @Operation(summary = "Busca feedback por ID")
    @GetMapping("/{id}")
    public ResponseEntity<FeedbackResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Cria novo feedback")
    @PostMapping
    public ResponseEntity<FeedbackResponseDTO> criar(@RequestBody @Valid FeedbackRequestDTO dto) {
        FeedbackResponseDTO salvo = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(salvo.id()).toUri();
        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(summary = "Atualiza feedback por ID")
    @PutMapping("/{id}")
    public ResponseEntity<FeedbackResponseDTO> atualizar(@PathVariable Integer id,
                                                         @RequestBody @Valid FeedbackRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Deleta feedback por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
