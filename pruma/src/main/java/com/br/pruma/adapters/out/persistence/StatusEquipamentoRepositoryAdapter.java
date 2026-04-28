package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.StatusEquipamento;
import com.br.pruma.core.repository.StatusEquipamentoRepository;
import com.br.pruma.core.repository.port.StatusEquipamentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link StatusEquipamentoRepositoryPort}
 * delegando ao {@link StatusEquipamentoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class StatusEquipamentoRepositoryAdapter implements StatusEquipamentoRepositoryPort {

    private final StatusEquipamentoRepository statusEquipamentoRepository;

    @Override
    public StatusEquipamento save(StatusEquipamento statusEquipamento) {
        return statusEquipamentoRepository.save(statusEquipamento);
    }

    @Override
    public Optional<StatusEquipamento> findById(Integer id) {
        return statusEquipamentoRepository.findById(id);
    }

    @Override
    public List<StatusEquipamento> findAll() {
        return statusEquipamentoRepository.findAll();
    }

    @Override
    public Page<StatusEquipamento> findAll(Pageable pageable) {
        return statusEquipamentoRepository.findAll(pageable);
    }

    @Override
    public void delete(StatusEquipamento statusEquipamento) {
        statusEquipamentoRepository.delete(statusEquipamento);
    }

    @Override
    public void deleteById(Integer id) {
        statusEquipamentoRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return statusEquipamentoRepository.existsById(id);
    }
}