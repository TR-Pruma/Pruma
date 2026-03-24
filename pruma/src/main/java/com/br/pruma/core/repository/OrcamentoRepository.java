package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Orcamento;
import com.br.pruma.core.enums.StatusOrcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Integer> {

    /**
     * Busca orçamentos por projeto.
     */
    List<Orcamento> findAllByProjeto_Id(Integer projetoId);

    /**
     * Busca orçamentos por CNPJ de empresa.
     */
    List<Orcamento> findAllByEmpresa_Cnpj(String empresaCnpj);

    /**
     * Busca orçamentos por status.
     */
    List<Orcamento> findAllByStatus(StatusOrcamento status);
}