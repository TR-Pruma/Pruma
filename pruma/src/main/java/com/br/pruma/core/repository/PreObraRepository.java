package com.br.pruma.core.repository;

import com.br.pruma.core.domain.PreObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PreObraRepository extends JpaRepository<PreObra, Integer> {

    /**
     * Lista todos os registros de pré-obra associados a uma obra.
     */
    List<PreObra> findAllByObra_Id(Integer obraId);

    /**
     * Lista registros cuja data de início esteja entre as datas informadas (inclusive).
     */
    List<PreObra> findAllByDataInicioBetween(LocalDate start, LocalDate end);

    /**
     * Lista registros com data de início igual à informada.
     */
    List<PreObra> findAllByDataInicio(LocalDate date);

    /**
     * Busca por descrição contendo o texto informado (case-insensitive).
     */
    List<PreObra> findAllByDescricaoContainingIgnoreCase(String descricao);

    /**
     * Verifica se existem registros de pré-obra para a obra informada.
     */
    boolean existsByObra_Id(Integer obraId);
}