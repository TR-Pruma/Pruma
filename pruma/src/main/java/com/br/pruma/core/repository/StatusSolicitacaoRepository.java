package com.br.pruma.core.repository;

import com.br.pruma.core.domain.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusSolicitacaoRepository extends JpaRepository<StatusSolicitacao, Integer> {
}
