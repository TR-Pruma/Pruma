package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.StatusSolicitacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de StatusSolicitacao.
 */
public interface StatusSolicitacaoRepositoryPort {

    StatusSolicitacao save(StatusSolicitacao statusSolicitacao);

    Optional<StatusSolicitacao> findById(Integer id);

    List<StatusSolicitacao> findAll();

    Page<StatusSolicitacao> findAll(Pageable pageable);

    void delete(StatusSolicitacao statusSolicitacao);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}