package com.br.pruma.core.repository;

import com.br.pruma.core.domain.MensagemInstantaneaAux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MensagemInstantaneaAuxRepository extends JpaRepository<MensagemInstantaneaAux, Integer> {

    /**
     * Recupera os metadados auxiliares de uma mensagem instantânea.
     */
    Optional<MensagemInstantaneaAux> findByMensagem_Id(Integer mensagemId);
}
