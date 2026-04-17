package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.PreObra;
import com.br.pruma.core.repository.PreObraRepository;
import com.br.pruma.core.repository.port.PreObraRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PreObraRepositoryAdapter implements PreObraRepositoryPort {

    private final PreObraRepository preObraRepository;

    @Override
    public PreObra save(PreObra preObra) {
        return preObraRepository.save(preObra);
    }

    @Override
    public Optional<PreObra> findById(Integer id) {
        return preObraRepository.findById(id);
    }

    @Override
    public List<PreObra> findAll() {
        return preObraRepository.findAll();
    }

    @Override
    public Page<PreObra> findAll(Pageable pageable) {
        return preObraRepository.findAll(pageable);
    }

    @Override
    public List<PreObra> findAllByObra_Id(Integer obraId) {
        return preObraRepository.findAllByObra_Id(obraId);
    }

    @Override
    public List<PreObra> findAllByDataInicioBetween(LocalDate startDate, LocalDate endDate) {
        return preObraRepository.findAllByDataInicioBetween(startDate, endDate);
    }

    @Override
    public List<PreObra> findAllByDataInicio(LocalDate date) {
        return preObraRepository.findAllByDataInicio(date);
    }

    @Override
    public void deleteById(Integer id) {
        preObraRepository.deleteById(id);
    }

    @Override
    public void delete(PreObra preObra) {
        preObraRepository.delete(preObra);
    }

    @Override
    public boolean existsById(Integer id) {
        return preObraRepository.existsById(id);
    }
}