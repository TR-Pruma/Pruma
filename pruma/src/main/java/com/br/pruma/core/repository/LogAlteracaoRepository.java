package com.br.pruma.core.repository;

import com.br.pruma.core.domain.LogAlteracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogAlteracaoRepository extends JpaRepository<LogAlteracao, Integer> {

    /**
     * Retorna todos os logs de um projeto, ordenados do mais recente para o mais antigo.
     */
    List<LogAlteracao> findByProjeto_IdOrderByDataHoraDesc(Integer projetoId);

    /**
     * Retorna todos os logs de um cliente, ordenados do mais recente para o mais antigo.
     */
    List<LogAlteracao> findByCliente_CpfOrderByDataHoraDesc(String clienteCpf);

    /**
     * Retorna todos os logs de um tipo de usuário, ordenados do mais recente para o mais antigo.
     */
    List<LogAlteracao> findByTipoUsuario_IdOrderByDataHoraDesc(Integer tipoUsuarioId);
}