package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Feedback;
import com.br.pruma.core.repository.FeedbackRepository;
import com.br.pruma.core.repository.port.FeedbackRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link FeedbackRepositoryPort}
 * delegando ao {@link FeedbackRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class FeedbackRepositoryAdapter implements FeedbackRepositoryPort {

    private final FeedbackRepository feedbackRepository;

    @Override
    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public Optional<Feedback> findById(Integer id) {
        return feedbackRepository.findById(id);
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    @Override
    public Page<Feedback> findAll(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer id) {
        feedbackRepository.deleteById(id);
    }

    @Override
    public void delete(Feedback feedback) {
        feedbackRepository.delete(feedback);
    }

    @Override
    public boolean existsById(Integer id) {
        return feedbackRepository.existsById(id);
    }
}
