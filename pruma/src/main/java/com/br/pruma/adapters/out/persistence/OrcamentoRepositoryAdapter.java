package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Orcamento;
import com.br.pruma.core.enums.StatusOrcamento;
import com.br.pruma.core.repository.OrcamentoRepository;
import com.br.pruma.core.repository.port.OrcamentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link OrcamentoRepositoryPort}
 * delegando ao {@link OrcamentoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class OrcamentoRepositoryAdapter implements OrcamentoRepositoryPort {

    private final OrcamentoRepository orcamentoRepository;

    @Override
    public Orcamento save(Orcamento orcamento) {
        return orcamentoRepository.save(orcamento);
    }

    @Override
    public Optional<Orcamento> findById(Integer id) {
        return orcamentoRepository.findById(id);
    }

    @Override
    public List<Orcamento> findAll() {
        return orcamentoRepository.findAll();
    }

    @Override
    public Page<Orcamento> findAll(Pageable pageable) {
        return orcamentoRepository.findAll(pageable);
    }

    @Override
    public List<Orcamento> findAllByProjeto_Id(Integer projetoId) {
        return orcamentoRepository.findAllByProjeto_Id(projetoId);
    }

    @Override
    public List<Orcamento> findAllByEmpresa_Cnpj(String empresaCnpj) {
        return orcamentoRepository.findAllByEmpresa_Cnpj(empresaCnpj);
    }

    @Override
    public List<Orcamento> findAllByStatus(StatusOrcamento status) {
        return orcamentoRepository.findAllByStatus(status);
    }

    @Override
    public void deleteById(Integer id) {
        orcamentoRepository.deleteById(id);
    }

    @Override
    public void delete(Orcamento orcamento) {
        orcamentoRepository.delete(orcamento);
    }

    @Override
    public boolean existsById(Integer id) {
        return orcamentoRepository.existsById(id);
    }
}
