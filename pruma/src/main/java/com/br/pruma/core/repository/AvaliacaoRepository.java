package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
    List<Avaliacao> findByProjetoId(Integer projetoId);
    List<Avaliacao> findByClienteCpf(String clienteCpf);
}
