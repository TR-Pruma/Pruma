package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Feedback.
 */
public interface FeedbackRepositoryPort {

    Feedback save(Feedback feedback);

    Optional<Feedback> findById(Integer id);

    List<Feedback> findAll();

    Page<Feedback> findAll(Pageable pageable);

    void deleteById(Integer id);

    void delete(Feedback feedback);

    boolean existsById(Integer id);
}
