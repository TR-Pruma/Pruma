package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.FeedbackRequestDTO;
import com.br.pruma.application.dto.response.FeedbackResponseDTO;
import com.br.pruma.application.mapper.FeedbackMapper;
import com.br.pruma.core.domain.Feedback;
import com.br.pruma.core.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository repository;
    private final FeedbackMapper mapper;

    @Transactional
    public FeedbackResponseDTO salvar(FeedbackRequestDTO dto) {
        Feedback feedback = mapper.toEntity(dto);
        return mapper.toResponseDTO(repository.save(feedback));
    }

    public List<FeedbackResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }
    public FeedbackResponseDTO buscarPorId(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Feedback não encontrado"));
    }



}
