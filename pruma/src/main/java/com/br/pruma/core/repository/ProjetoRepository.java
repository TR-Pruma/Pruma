package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Projeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
     * Lista projetos cuja data de criação esteja entre as datas informadas (inclusive), com paginação.
     */
    Page<Projeto> findAllByDataCriacaoBetween(LocalDate start, LocalDate end, Pageable pageable);

    /**
     * Lista projetos com data de criação igual à informada, com paginação.
     */
    Page<Projeto> findAllByDataCriacao(LocalDate date, Pageable pageable);

    // @SQLRestriction("ativo = true") da AuditableEntity já filtra automaticamente;
    // o sufixo AndAtivoTrue é redundante mas mantido para semântica explícita no Service.
    Optional<Projeto> findByIdAndAtivoTrue(Integer projetoId);

    boolean existsByIdAndAtivoTrue(Integer projetoId);
}
