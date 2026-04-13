package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.EquipamentoProjeto;
import com.br.pruma.core.repository.EquipamentoProjetoRepository;
import com.br.pruma.core.repository.port.EquipamentoProjetoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link EquipamentoProjetoRepositoryPort}
 * delegando ao {@link EquipamentoProjetoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class EquipamentoProjetoRepositoryAdapter implements EquipamentoProjetoRepositoryPort {

    private final EquipamentoProjetoRepository equipamentoProjetoRepository;

    @Override
    public EquipamentoProjeto save(EquipamentoProjeto equipamentoProjeto) {
        return equipamentoProjetoRepository.save(equipamentoProjeto);
    }

    @Override
    public Optional<EquipamentoProjeto> findById(Long id) {
        return equipamentoProjetoRepository.findById(id);
    }

    @Override
    public List<EquipamentoProjeto> findAll() {
        return equipamentoProjetoRepository.findAll();
    }

    @Override
    public Page<EquipamentoProjeto> findAll(Pageable pageable) {
        return equipamentoProjetoRepository.findAll(pageable);
    }

    @Override
    public boolean existsByEquipamentoIdAndDataAlocacao(Long equipamentoId, LocalDate dataAlocacao) {
        return equipamentoProjetoRepository.existsByEquipamentoIdAndDataAlocacao(equipamentoId, dataAlocacao);
    }

    @Override
    public void deleteById(Long id) {
        equipamentoProjetoRepository.deleteById(id);
    }

    @Override
    public void delete(EquipamentoProjeto equipamentoProjeto) {
        equipamentoProjetoRepository.delete(equipamentoProjeto);
    }

    @Override
    public boolean existsById(Long id) {
        return equipamentoProjetoRepository.existsById(id);
    }
}
