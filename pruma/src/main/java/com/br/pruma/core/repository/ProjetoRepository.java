package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

    /**
     * Busca projetos cujo nome contenha o texto informado (case-insensitive).
     */
    List<Projeto> findAllByNomeContainingIgnoreCase(String nome);

    /**
     * Busca projeto pelo nome exato.
     */
    Optional<Projeto> findByNome(String nome);

    /**
     * Verifica existência por nome exato.
     */
    boolean existsByNome(String nome);

    /**
     * Lista projetos cuja data de criação esteja entre as datas informadas (inclusive).
     */
    List<Projeto> findAllByDataCriacaoBetween(LocalDate start, LocalDate end);

    /**
     * Lista projetos com data de criação igual à informada.
     */
    List<Projeto> findAllByDataCriacao(LocalDate date);
}

