package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.LogAlteracaoAux;
import com.br.pruma.core.enums.TipoAlteracao;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de LogAlteracaoAux.
 * Define o contrato que a camada de aplicação usa sem depender
 * diretamente de JPA ou de qualquer tecnologia de persistência.
 */
public interface LogAlteracaoAuxRepositoryPort {

    /**
     * Persiste ou atualiza um LogAlteracaoAux.
     */
    LogAlteracaoAux save(LogAlteracaoAux logAlteracaoAux);

    /**
     * Busca um LogAlteracaoAux pelo seu ID.
     */
    Optional<LogAlteracaoAux> findById(Integer id);

    /**
     * Retorna todos os registros de LogAlteracaoAux.
     */
    List<LogAlteracaoAux> findAll();

    /**
     * Retorna todos os registros filtrados por tipo de alteração.
     */
    List<LogAlteracaoAux> findByTipoAlteracao(TipoAlteracao tipoAlteracao);

    /**
     * Remove um LogAlteracaoAux.
     */
    void delete(LogAlteracaoAux logAlteracaoAux);

    /**
     * Verifica se um LogAlteracaoAux existe pelo seu ID.
     */
    boolean existsById(Integer id);
}
