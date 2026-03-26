package com.br.pruma.core.repository;


import com.br.pruma.core.domain.SolicitacaoMudanca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoMudancaRepository extends JpaRepository<SolicitacaoMudanca, Integer> {

    /**
     * Busca solicitações de mudança de um projeto específico.
     */
    List<SolicitacaoMudanca> findByProjetoId(Integer projetoId);

    /**
     * Busca solicitações de mudança por status específico.
     */
    List<SolicitacaoMudanca> findByStatusSolicitacaoId(Integer statusSolicitacaoId);
}

