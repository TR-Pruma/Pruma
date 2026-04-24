package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Orcamento;
import com.br.pruma.core.enums.StatusOrcamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Orcamento.
 */
public interface OrcamentoRepositoryPort {

    Orcamento save(Orcamento orcamento);

    Optional<Orcamento> findById(Integer id);

    List<Orcamento> findAll();

    Page<Orcamento> findAll(Pageable pageable);

    /** Busca orçamentos por projeto. */
    List<Orcamento> findAllByProjetoId(Integer projetoId);

    /** Busca orçamentos pelo CNPJ da empresa. */
    List<Orcamento> findAllByEmpresaCnpj(String empresaCnpj);

    /** Busca orçamentos pelo status. */
    List<Orcamento> findAllByStatus(StatusOrcamento status);

    void deleteById(Integer id);

    void delete(Orcamento orcamento);

    boolean existsById(Integer id);
}
