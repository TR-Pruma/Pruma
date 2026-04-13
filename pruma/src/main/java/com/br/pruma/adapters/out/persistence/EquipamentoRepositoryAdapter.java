package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Equipamento;
import com.br.pruma.core.enums.StatusEquipamento;
import com.br.pruma.core.repository.EquipamentoRepository;
import com.br.pruma.core.repository.port.EquipamentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link EquipamentoRepositoryPort}
 * delegando ao {@link EquipamentoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class EquipamentoRepositoryAdapter implements EquipamentoRepositoryPort {

    private final EquipamentoRepository equipamentoRepository;

    @Override
    public Equipamento save(Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }

    @Override
    public Optional<Equipamento> findById(Integer id) {
        return equipamentoRepository.findById(id);
    }

    @Override
    public Optional<Equipamento> findByIdIncludingInativos(Integer id) {
        return equipamentoRepository.findByIdIncludingInativos(id);
    }

    @Override
    public List<Equipamento> findAll() {
        return equipamentoRepository.findAll();
    }

    @Override
    public Page<Equipamento> findAll(Pageable pageable) {
        return equipamentoRepository.findAll(pageable);
    }

    @Override
    public Page<Equipamento> searchIncludingInativos(String nome, StatusEquipamento status, Boolean ativo, Pageable pageable) {
        return equipamentoRepository.searchIncludingInativos(nome, status, ativo, pageable);
    }

    @Override
    public void softDeleteManyByIds(List<Integer> ids) {
        equipamentoRepository.softDeleteManyByIds(ids);
    }

    @Override
    public void reativarManyByIds(List<Integer> ids) {
        equipamentoRepository.reativarManyByIds(ids);
    }

    @Override
    public void deleteById(Integer id) {
        equipamentoRepository.deleteById(id);
    }

    @Override
    public void delete(Equipamento equipamento) {
        equipamentoRepository.delete(equipamento);
    }

    @Override
    public boolean existsById(Integer id) {
        return equipamentoRepository.existsById(id);
    }
}
