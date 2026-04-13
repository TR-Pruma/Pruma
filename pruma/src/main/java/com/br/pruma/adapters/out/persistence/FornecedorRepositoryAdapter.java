package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Fornecedor;
import com.br.pruma.core.repository.FornecedorRepository;
import com.br.pruma.core.repository.port.FornecedorRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link FornecedorRepositoryPort}
 * delegando ao {@link FornecedorRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class FornecedorRepositoryAdapter implements FornecedorRepositoryPort {

    private final FornecedorRepository fornecedorRepository;

    @Override
    public Fornecedor save(Fornecedor fornecedor) {
        return fornecedorRepository.save(fornecedor);
    }

    @Override
    public Optional<Fornecedor> findById(Integer id) {
        return fornecedorRepository.findById(id);
    }

    @Override
    public List<Fornecedor> findAll() {
        return fornecedorRepository.findAll();
    }

    @Override
    public Page<Fornecedor> findAll(Pageable pageable) {
        return fornecedorRepository.findAll(pageable);
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return fornecedorRepository.existsByCnpj(cnpj);
    }

    @Override
    public void deleteById(Integer id) {
        fornecedorRepository.deleteById(id);
    }

    @Override
    public void delete(Fornecedor fornecedor) {
        fornecedorRepository.delete(fornecedor);
    }

    @Override
    public boolean existsById(Integer id) {
        return fornecedorRepository.existsById(id);
    }
}
