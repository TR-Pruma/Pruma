package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.LogAlteracao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de LogAlteracao.
 */
public interface LogAlteracaoRepositoryPort {

    LogAlteracao save(LogAlteracao logAlteracao);

    Optional<LogAlteracao> findById(Integer id);

    List<LogAlteracao> findAll();

    Page<LogAlteracao> findAll(Pageable pageable);

    /** Retorna logs de um projeto paginados, do mais recente ao mais antigo. */
    Page<LogAlteracao> findByProjetoIdOrderByDataHoraDesc(Integer projetoId, Pageable pageable);

    /** Retorna logs de um cliente paginados, do mais recente ao mais antigo. */
    Page<LogAlteracao> findByClienteCpfOrderByDataHoraDesc(String clienteCpf, Pageable pageable);

    /** Retorna logs de um tipo de usuário paginados, do mais recente ao mais antigo. */
    Page<LogAlteracao> findByTipoUsuarioIdOrderByDataHoraDesc(Integer tipoUsuarioId, Pageable pageable);

    void deleteById(Integer id);

    void delete(LogAlteracao logAlteracao);

    boolean existsById(Integer id);
}
