package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.ItemOrcamento;
import com.br.pruma.core.repository.ItemOrcamentoRepository;
import com.br.pruma.core.repository.port.ItemOrcamentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída (Output Adapter) que implementa o {@link ItemOrcamentoRepositoryPort}
 * delegando as operações ao {@link ItemOrcamentoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class ItemOrcamentoRepositoryAdapter implements ItemOrcamentoRepositoryPort {

    private final ItemOrcamentoRepository itemOrcamentoRepository;

    @Override
    public ItemOrcamento save(ItemOrcamento itemOrcamento) {
        return itemOrcamentoRepository.save(itemOrcamento);
    }

    @Override
    public Optional<ItemOrcamento> findById(Integer id) {
        return itemOrcamentoRepository.findById(id);
    }

    @Override
    public List<ItemOrcamento> findAll() {
        return itemOrcamentoRepository.findAll();
    }

    @Override
    public List<ItemOrcamento> findByOrcamentoId(Integer orcamentoId) {
        return itemOrcamentoRepository.findByOrcamento_Id(orcamentoId);
    }

    @Override
    public void delete(ItemOrcamento itemOrcamento) {
        itemOrcamentoRepository.delete(itemOrcamento);
    }

    @Override
    public boolean existsById(Integer id) {
        return itemOrcamentoRepository.existsById(id);
    }

    @Override
    public Page<ItemOrcamento> findAll(Pageable pageable) {
        return itemOrcamentoRepository.findAll(pageable);
    }
}
