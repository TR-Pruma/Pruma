package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.repository.AtividadeRepository;
import com.br.pruma.core.repository.port.AtividadeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link AtividadeRepositoryPort}
 * delegando ao {@link AtividadeRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class AtividadeRepositoryAdapter implements AtividadeRepositoryPort {

    private final AtividadeRepository atividadeRepository;

    @Override
    public Atividade save(Atividade atividade) {
        return atividadeRepository.save(atividade);
    }

    @Override
    public Optional<Atividade> findById(Integer id) {
        return atividadeRepository.findById(id);
    }

    @Override
    public List<Atividade> findAll() {
        return atividadeRepository.findAll();
    }

    @Override
    public Page<Atividade> findAll(Pageable pageable) {
        return atividadeRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer id) {
        atividadeRepository.deleteById(id);
    }

    @Override
    public void delete(Atividade atividade) {
        atividadeRepository.delete(atividade);
    }

    @Override
    public boolean existsById(Integer id) {
        return atividadeRepository.existsById(id);
    }
}
