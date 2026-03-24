package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Obra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ObraRepository extends JpaRepository<Obra, Integer> {

    /**
     * Lista todas as obras associadas a um projeto.
     */
    List<Obra> findAllByProjeto_Id(Integer projetoId);

    /**
     * Procura obras cuja data de início esteja entre as datas informadas (inclusive).
     */
    List<Obra> findAllByDataInicioBetween(LocalDate start, LocalDate end);

    /**
     * Procura obras pela descrição (LIKE %descricao%), case-insensitive.
     */
    List<Obra> findAllByDescricaoContainingIgnoreCase(String descricao);

    /**
     * Verifica existência de obras para um determinado projeto.
     */
    boolean existsByProjeto_Id(Integer projetoId);
}
