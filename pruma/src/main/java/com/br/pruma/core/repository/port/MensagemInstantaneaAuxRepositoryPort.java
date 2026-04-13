package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.MensagemInstantaneaAux;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de MensagemInstantaneaAux.
 */
public interface MensagemInstantaneaAuxRepositoryPort {

    MensagemInstantaneaAux save(MensagemInstantaneaAux mensagemInstantaneaAux);

    Optional<MensagemInstantaneaAux> findById(Integer id);

    List<MensagemInstantaneaAux> findAll();

    Page<MensagemInstantaneaAux> findAll(Pageable pageable);

    /** Recupera os metadados auxiliares de uma mensagem instantânea pelo ID da mensagem. */
    Optional<MensagemInstantaneaAux> findByMensagemId(Integer mensagemId);

    void deleteById(Integer id);

    void delete(MensagemInstantaneaAux mensagemInstantaneaAux);

    boolean existsById(Integer id);
}
