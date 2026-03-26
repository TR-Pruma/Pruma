package com.br.pruma.core.repository;

import com.br.pruma.core.domain.LogAlteracao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogAlteracaoRepository extends JpaRepository<LogAlteracao, Integer> {

    /**
     * Retorna logs de um projeto paginados, do mais recente para o mais antigo.
     */
    Page<LogAlteracao> findByProjeto_IdOrderByDataHoraDesc(Integer projetoId, Pageable pageable);

    /**
     * Retorna logs de um cliente paginados, do mais recente para o mais antigo.
     */
    Page<LogAlteracao> findByCliente_CpfOrderByDataHoraDesc(String clienteCpf, Pageable pageable);

    /**
     * Retorna logs de um tipo de usuário paginados, do mais recente para o mais antigo.
     */
    Page<LogAlteracao> findByTipoUsuario_IdOrderByDataHoraDesc(Integer tipoUsuarioId, Pageable pageable);
}
