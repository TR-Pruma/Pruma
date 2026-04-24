package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.InsumoFornecedor;
import com.br.pruma.core.domain.InsumoFornecedorAuxId;
import com.br.pruma.core.repository.InsumoFornecedorRepository;
import com.br.pruma.core.repository.port.InsumoFornecedorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link InsumoFornecedorRepositoryPort}
 * delegando ao {@link InsumoFornecedorRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class InsumoFornecedorRepositoryAdapter implements InsumoFornecedorRepositoryPort {

    private final InsumoFornecedorRepository insumoFornecedorRepository;

    @Override
    public InsumoFornecedor save(InsumoFornecedor insumoFornecedor) {
        return insumoFornecedorRepository.save(insumoFornecedor);
    }

    @Override
    public Optional<InsumoFornecedor> findById(InsumoFornecedorAuxId id) {
        return insumoFornecedorRepository.findById(id);
    }

    @Override
    public List<InsumoFornecedor> findAll() {
        return insumoFornecedorRepository.findAll();
    }

    @Override
    public Page<InsumoFornecedor> findAll(Pageable pageable) {
        return insumoFornecedorRepository.findAll(pageable);
    }

    @Override
    public List<InsumoFornecedor> findByIdInsumoId(Integer insumoId) {
        return insumoFornecedorRepository.findByIdInsumoId(insumoId);
    }

    @Override
    public void deleteById(InsumoFornecedorAuxId id) {
        insumoFornecedorRepository.deleteById(id);
    }

    @Override
    public void delete(InsumoFornecedor insumoFornecedor) {
        insumoFornecedorRepository.delete(insumoFornecedor);
    }

    @Override
    public boolean existsById(InsumoFornecedorAuxId id) {
        return insumoFornecedorRepository.existsById(id);
    }
}
