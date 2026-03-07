package com.br.pruma.core.repository;


import com.br.pruma.core.domain.Equipamento;
import com.br.pruma.core.enums.StatusEquipamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {

    /**
     * Busca equipamentos incluindo os inativos, com filtros opcionais.
     * Se algum parâmetro for nulo, ele é ignorado no filtro.
     */
    @Query("""
        SELECT e FROM Equipamento e
        WHERE (:nome IS NULL OR LOWER(e.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
          AND (:status IS NULL OR e.status = :status)
          AND (:ativo IS NULL OR e.ativo = :ativo)
    """)
    Page<Equipamento> searchIncludingInativos(@Param("nome") String nome,
                                              @Param("status") StatusEquipamento status,
                                              @Param("ativo") Boolean ativo,
                                              Pageable pageable);

    /**
     * Busca por ID incluindo inativos.
     */
    @Query("SELECT e FROM Equipamento e WHERE e.id = :id")
    Optional<Equipamento> findByIdIncludingInativos(@Param("id") Integer id);

    /**
     * Soft delete em lote (marca como inativo).
     */
    @Modifying
    @Query("UPDATE Equipamento e SET e.ativo = false WHERE e.id IN :ids")
    void softDeleteManyByIds(@Param("ids") List<Integer> ids);

    /**
     * Reativar em lote (marca como ativo).
     */
    @Modifying
    @Query("UPDATE Equipamento e SET e.ativo = true WHERE e.id IN :ids")
    void reativarManyByIds(@Param("ids") List<Integer> ids);
}
