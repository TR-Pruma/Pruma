package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.repository.ObraRepository;
import com.br.pruma.core.repository.port.ObraRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link ObraRepositoryPort}
 * delegando ao {@link ObraRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class ObraRepositoryAdapter implements ObraRepositoryPort {

    private final ObraRepository obraRepository;

    @Override
    public Obra save(Obra obra) {
        return obraRepository.save(obra);
    }

    @Override
    public Optional<Obra> findById(Integer id) {
        return obraRepository.findById(id);
    }

    @Override
    public List<Obra> findAll() {
        return obraRepository.findAll();
    }

    @Override
    public Page<Obra> findAll(Pageable pageable) {
        return obraRepository.findAll(pageable);
    }

    @Override
    public List<Obra> findAllByProjetoId(Integer projetoId) {
        return obraRepository.findAllByProjeto_Id(projetoId);
    }

    @Override
    public List<Obra> findAllByDataInicioBetween(LocalDate start, LocalDate end) {
        return obraRepository.findAllByDataInicioBetween(start, end);
    }

    @Override
    public List<Obra> findAllByDescricaoContainingIgnoreCase(String descricao) {
        return obraRepository.findAllByDescricaoContainingIgnoreCase(descricao);
    }

    @Override
    public boolean existsByProjetoId(Integer projetoId) {
        return obraRepository.existsByProjeto_Id(projetoId);
    }

    @Override
    public void deleteById(Integer id) {
        obraRepository.deleteById(id);
    }

    @Override
    public void delete(Obra obra) {
        obraRepository.delete(obra);
    }

    @Override
    public boolean existsById(Integer id) {
        return obraRepository.existsById(id);
    }
}
