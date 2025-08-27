package com.br.pruma.core.repository;

import com.br.pruma.core.domain.LogAlteracaoAux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogAlteracaoAuxRepository extends JpaRepository<LogAlteracaoAux, Integer> {

    /**
     * Retorna todos os registros auxiliares de log com um tipo de alteração específico.
     */
    List<LogAlteracaoAux> findByTipoAlteracao(String tipoAlteracao);
}
