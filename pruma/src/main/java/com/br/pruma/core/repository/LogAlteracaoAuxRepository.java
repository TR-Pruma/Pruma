package com.br.pruma.core.repository;

import com.br.pruma.core.domain.LogAlteracaoAux;
import com.br.pruma.core.enums.TipoAlteracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogAlteracaoAuxRepository extends JpaRepository<LogAlteracaoAux, Integer> {

    List<LogAlteracaoAux> findByTipoAlteracao(TipoAlteracao tipoAlteracao);
}
