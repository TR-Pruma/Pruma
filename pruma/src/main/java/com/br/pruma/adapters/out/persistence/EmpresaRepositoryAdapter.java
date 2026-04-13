package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Empresa;
import com.br.pruma.core.repository.EmpresaRepository;
import com.br.pruma.core.repository.port.EmpresaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link EmpresaRepositoryPort}
 * delegando ao {@link EmpresaRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class EmpresaRepositoryAdapter implements EmpresaRepositoryPort {

    private final EmpresaRepository empresaRepository;

    @Override
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public Optional<Empresa> findById(String cnpj) {
        return empresaRepository.findById(cnpj);
    }

    @Override
    public Optional<Empresa> findByCnpj(String cnpj) {
        return empresaRepository.findByCnpj(cnpj);
    }

    @Override
    public List<Empresa> findAll() {
        return empresaRepository.findAll();
    }

    @Override
    public Page<Empresa> findAll(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    @Override
    public void deleteByCnpj(String cnpj) {
        empresaRepository.deleteByCnpj(cnpj);
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return empresaRepository.existsByCnpj(cnpj);
    }

    @Override
    public void deleteById(String cnpj) {
        empresaRepository.deleteById(cnpj);
    }

    @Override
    public void delete(Empresa empresa) {
        empresaRepository.delete(empresa);
    }

    @Override
    public boolean existsById(String cnpj) {
        return empresaRepository.existsById(cnpj);
    }
}
