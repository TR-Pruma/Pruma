package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.PosObra;
import com.br.pruma.core.repository.PosObraRepository;
import com.br.pruma.core.repository.port.PosObraRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link PosObraRepositoryPort}
 * delegando ao {@link PosObraRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class PosObraRepositoryAdapter implements PosObraRepositoryPort {

    private final PosObraRepository posObraRepository;

    @Override
    public PosObra save(PosObra posObra) {
        return posObraRepository.save(posObra);
    }

    @Override
    public Optional<PosObra> findById(Long id) {
        return posObraRepository.findById(id);
    }

    @Override
    public List<PosObra> findAll() {
        return posObraRepository.findAll();
    }

    @Override
    public Page<PosObra> findAll(Pageable pageable) {
        return posObraRepository.findAll(pageable);
    }

    @Override
    public List<PosObra> findAllByObra_Id(Long obraId) {
        return posObraRepository.findAllByObra_Id(obraId);
    }

    @Override
    public List<PosObra> findAllByDataConclusaoBetween(LocalDate startDate, LocalDate endDate) {
        return posObraRepository.findAllByDataConclusaoBetween(startDate, endDate);
    }

    @Override
    public List<PosObra> findAllByDataConclusao(LocalDate date) {
        return posObraRepository.findAllByDataConclusao(date);
    }

    @Override
    public void deleteById(Long id) {
        posObraRepository.deleteById(id);
    }

    @Override
    public void delete(PosObra posObra) {
        posObraRepository.delete(posObra);
    }

    @Override
    public boolean existsById(Long id) {
        return posObraRepository.existsById(id);
    }
}
