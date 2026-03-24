package com.br.pruma.adapters.in.rest;

import com.br.pruma.application.dto.request.FeedbackRequestDTO;
import com.br.pruma.application.dto.response.FeedbackResponseDTO;
import com.br.pruma.application.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackResponseDTO criar(@Valid @RequestBody FeedbackRequestDTO dto) {
        return service.salvar(dto);
    }
    @GetMapping
    public List<FeedbackResponseDTO> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public FeedbackResponseDTO buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }
}