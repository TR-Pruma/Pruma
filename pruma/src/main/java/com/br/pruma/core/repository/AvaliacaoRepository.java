package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {

    List<Avaliacao> findByProjetoId(Integer projetoId);

    /** Avaliacoes realizadas por um usuario (avaliador) */
    List<Avaliacao> findByAvaliadorId(Integer avaliadorId);

    /** Avaliacoes recebidas por um usuario (avaliado) */
    List<Avaliacao> findByAvaliadoId(Integer avaliadoId);
}
