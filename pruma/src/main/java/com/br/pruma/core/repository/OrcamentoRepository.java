package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Integer> {

    /**
     * Busca todos os orçamentos de um projeto específico.
     */
    List<Orcamento> findByProjeto_Id(Integer projetoId);
}
