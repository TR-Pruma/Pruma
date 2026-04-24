package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Cronograma;
import com.br.pruma.core.repository.CronogramaRepository;
import com.br.pruma.core.repository.port.CronogramaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link CronogramaRepositoryPort}
 * delegando ao {@link CronogramaRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class CronogramaRepositoryAdapter implements CronogramaRepositoryPort {

    private final CronogramaRepository cronogramaRepository;

    @Override
    public Cronograma save(Cronograma cronograma) {
        return cronogramaRepository.save(cronograma);
    }

    @Override
    public Optional<Cronograma> findById(Integer id) {
        return cronogramaRepository.findById(id);
    }

    @Override
    public List<Cronograma> findAll() {
        return cronogramaRepository.findAll();
    }

    @Override
    public Page<Cronograma> findAll(Pageable pageable) {
        return cronogramaRepository.findAll(pageable);
    }

    @Override
    public List<Cronograma> findAllByProjetoId(Integer projetoId) {
        return cronogramaRepository.findAllByProjetoId(projetoId);
    }

    @Override
    public Optional<Cronograma> findByIdAndProjetoId(Integer id, Integer projetoId) {
        return cronogramaRepository.findByIdAndProjetoId(id, projetoId);
    }

    @Override
    public void deleteById(Integer id) {
        cronogramaRepository.deleteById(id);
    }

    @Override
    public void delete(Cronograma cronograma) {
        cronogramaRepository.delete(cronograma);
    }

    @Override
    public boolean existsById(Integer id) {
        return cronogramaRepository.existsById(id);
    }
}
