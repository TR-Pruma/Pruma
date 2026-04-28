package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Comunicacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Comunicacao.
 */
public interface ComunicacaoRepositoryPort {

    Comunicacao save(Comunicacao comunicacao);

    Optional<Comunicacao> findById(Integer id);

    /** Busca comunicação ativa pelo id. */
    Optional<Comunicacao> findByIdAndAtivoTrue(Integer id);

    /** Lista comunicações ativas de um projeto, ordenadas por data de criação desc. */
    Page<Comunicacao> findByProjetoIdAndAtivoTrueOrderByCreatedAtDesc(Integer projetoId, Pageable pageable);

    /** Lista comunicações ativas de um cliente, ordenadas por data de criação desc. */
    List<Comunicacao> findByClienteIdAndAtivoTrueOrderByCreatedAtDesc(Integer clienteId);

    /** Lista comunicações ativas por projeto e cliente, ordenadas por data de criação desc. */
    Page<Comunicacao> findByProjetoIdAndClienteIdAndAtivoTrueOrderByCreatedAtDesc(Integer projetoId, Integer clienteId, Pageable pageable);

    List<Comunicacao> findAll();

    Page<Comunicacao> findAll(Pageable pageable);

    /** Verifica se existe comunicação ativa entre projeto e cliente. */
    boolean existsByProjetoIdAndClienteIdAndAtivoTrue(Integer projetoId, Integer clienteId);

    void deleteById(Integer id);

    void delete(Comunicacao comunicacao);

    boolean existsById(Integer id);
}
