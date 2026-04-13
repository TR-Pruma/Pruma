package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.FaseCronograma;
import com.br.pruma.core.repository.FaseCronogramaRepository;
import com.br.pruma.core.repository.port.FaseCronogramaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link FaseCronogramaRepositoryPort}
 * delegando ao {@link FaseCronogramaRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class FaseCronogramaRepositoryAdapter implements FaseCronogramaRepositoryPort {

    private final FaseCronogramaRepository faseCronogramaRepository;

    @Override
    public FaseCronograma save(FaseCronograma faseCronograma) {
        return faseCronogramaRepository.save(faseCronograma);
    }

    @Override
    public Optional<FaseCronograma> findById(Integer id) {
        return faseCronogramaRepository.findById(id);
    }

    @Override
    public List<FaseCronograma> findAll() {
        return faseCronogramaRepository.findAll();
    }

    @Override
    public Page<FaseCronograma> findAll(Pageable pageable) {
        return faseCronogramaRepository.findAll(pageable);
    }

    @Override
    public List<FaseCronograma> findAllByCronograma_Id(Integer cronogramaId) {
        return faseCronogramaRepository.findAllByCronograma_Id(cronogramaId);
    }

    @Override
    public void deleteById(Integer id) {
        faseCronogramaRepository.deleteById(id);
    }

    @Override
    public void delete(FaseCronograma faseCronograma) {
        faseCronogramaRepository.delete(faseCronograma);
    }

    @Override
    public boolean existsById(Integer id) {
        return faseCronogramaRepository.existsById(id);
    }
}
