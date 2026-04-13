package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Inspecao;
import com.br.pruma.core.repository.InspecaoRepository;
import com.br.pruma.core.repository.port.InspecaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link InspecaoRepositoryPort}
 * delegando ao {@link InspecaoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class InspecaoRepositoryAdapter implements InspecaoRepositoryPort {

    private final InspecaoRepository inspecaoRepository;

    @Override
    public Inspecao save(Inspecao inspecao) {
        return inspecaoRepository.save(inspecao);
    }

    @Override
    public Optional<Inspecao> findById(Integer id) {
        return inspecaoRepository.findById(id);
    }

    @Override
    public List<Inspecao> findAll() {
        return inspecaoRepository.findAll();
    }

    @Override
    public Page<Inspecao> findAll(Pageable pageable) {
        return inspecaoRepository.findAll(pageable);
    }

    @Override
    public List<Inspecao> findAllByProjeto_Id(Integer projetoId) {
        return inspecaoRepository.findAllByProjeto_Id(projetoId);
    }

    @Override
    public void deleteById(Integer id) {
        inspecaoRepository.deleteById(id);
    }

    @Override
    public void delete(Inspecao inspecao) {
        inspecaoRepository.delete(inspecao);
    }

    @Override
    public boolean existsById(Integer id) {
        return inspecaoRepository.existsById(id);
    }
}
