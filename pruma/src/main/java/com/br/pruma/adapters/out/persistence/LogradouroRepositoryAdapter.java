package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Logradouro;
import com.br.pruma.core.repository.LogradouroRepository;
import com.br.pruma.core.repository.port.LogradouroRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link LogradouroRepositoryPort}
 * delegando ao {@link LogradouroRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class LogradouroRepositoryAdapter implements LogradouroRepositoryPort {

    private final LogradouroRepository logradouroRepository;

    @Override
    public Logradouro save(Logradouro logradouro) {
        return logradouroRepository.save(logradouro);
    }

    @Override
    public Optional<Logradouro> findById(Integer id) {
        return logradouroRepository.findById(id);
    }

    @Override
    public List<Logradouro> findAll() {
        return logradouroRepository.findAll();
    }

    @Override
    public Page<Logradouro> findAll(Pageable pageable) {
        return logradouroRepository.findAll(pageable);
    }

    @Override
    public Optional<Logradouro> findByTipo(String tipo) {
        return logradouroRepository.findByTipo(tipo);
    }

    @Override
    public List<Logradouro> findAllByOrderByTipoAsc() {
        return logradouroRepository.findAllByOrderByTipoAsc();
    }

    @Override
    public void deleteById(Integer id) {
        logradouroRepository.deleteById(id);
    }

    @Override
    public void delete(Logradouro logradouro) {
        logradouroRepository.delete(logradouro);
    }

    @Override
    public boolean existsById(Integer id) {
        return logradouroRepository.existsById(id);
    }
}
