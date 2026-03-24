package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.FeedbackRequestDTO;
import com.br.pruma.application.dto.response.FeedbackResponseDTO;
import com.br.pruma.application.service.FeedbackService;
import com.br.pruma.core.domain.Feedback;
import com.br.pruma.core.repository.FeedbackRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Override
    @Transactional
    public FeedbackResponseDTO criar(FeedbackRequestDTO request) {
        Feedback entity = Feedback.builder()
                .comentario(request.comentario())
                .nota(request.nota())
                .build();
        return toResponse(feedbackRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackResponseDTO> listarTodos() {
        return feedbackRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FeedbackResponseDTO buscarPorId(Integer id) {
        return feedbackRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Feedback não encontrado: " + id));
    }

    @Override
    @Transactional
    public FeedbackResponseDTO atualizar(Integer id, FeedbackRequestDTO request) {
        Feedback entity = feedbackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feedback não encontrado: " + id));
        entity.setComentario(request.comentario());
        entity.setNota(request.nota());
        return toResponse(feedbackRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!feedbackRepository.existsById(id)) {
            throw new EntityNotFoundException("Feedback não encontrado: " + id);
        }
        feedbackRepository.deleteById(id);
    }

    private FeedbackResponseDTO toResponse(Feedback e) {
        return new FeedbackResponseDTO(
                e.getId(),
                e.getComentario(),
                e.getNota(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
