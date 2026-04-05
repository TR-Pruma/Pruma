package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.LogAlteracao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de LogAlteracao.
 * Define o contrato que a camada de aplicação usa sem depender
 * diretamente de JPA ou de qualquer tecnologia de persistência.
 */
public interface LogAlteracaoRepositoryPort {

    /** Persiste ou atualiza um LogAlteracao. */
    LogAlteracao save(LogAlteracao logAlteracao);

    /** Busca um LogAlteracao pelo seu ID. */
    Optional<LogAlteracao> findById(Integer id);

    /** Retorna todos os registros de LogAlteracao. */
    List<LogAlteracao> findAll();

    /** Retorna página de registros de LogAlteracao. */
    Page<LogAlteracao> findAll(Pageable pageable);

    /** Retorna logs de um projeto paginados, do mais recente para o mais antigo. */
    Page<LogAlteracao> findByProjetoIdOrderByDataHoraDesc(Integer projetoId, Pageable pageable);

    /** Retorna logs de um cliente paginados, do mais recente para o mais antigo. */
    Page<LogAlteracao> findByClienteCpfOrderByDataHoraDesc(String clienteCpf, Pageable pageable);

    /** Retorna logs de um tipo de usuário paginados, do mais recente para o mais antigo. */
    Page<LogAlteracao> findByTipoUsuarioIdOrderByDataHoraDesc(Integer tipoUsuarioId, Pageable pageable);

    /** Remove um LogAlteracao pelo ID. */
    void deleteById(Integer id);

    /** Remove um LogAlteracao pela entidade. */
    void delete(LogAlteracao logAlteracao);

    /** Verifica se um LogAlteracao existe pelo seu ID. */
    boolean existsById(Integer id);
}
