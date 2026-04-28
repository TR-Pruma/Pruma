package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Insumo;
import com.br.pruma.core.repository.InsumoRepository;
import com.br.pruma.core.repository.port.InsumoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link InsumoRepositoryPort}
 * delegando ao {@link InsumoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class InsumoRepositoryAdapter implements InsumoRepositoryPort {

    private final InsumoRepository insumoRepository;

    @Override
    public Insumo save(Insumo insumo) {
        return insumoRepository.save(insumo);
    }

    @Override
    public Optional<Insumo> findById(Integer id) {
        return insumoRepository.findById(id);
    }

    @Override
    public List<Insumo> findAll() {
        return insumoRepository.findAll();
    }

    @Override
    public Page<Insumo> findAll(Pageable pageable) {
        return insumoRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Integer id) {
        insumoRepository.deleteById(id);
    }

    @Override
    public void delete(Insumo insumo) {
        insumoRepository.delete(insumo);
    }

    @Override
    public boolean existsById(Integer id) {
        return insumoRepository.existsById(id);
    }
}
