package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Checklist;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {

    /**
     * Busca todos os checklists de um projeto, carregando itens associados.
     * Nota: o filtro de ativo é gerenciado pelo @SQLRestriction da AuditableEntity.
     */
    @Query("SELECT c FROM Checklist c LEFT JOIN FETCH c.itens WHERE c.projeto.id = :projetoId")
    List<Checklist> findByProjetoIdWithItens(@Param("projetoId") Integer projetoId);

    /**
     * Busca checklist por id, carregando itens associados.
     * Nota: o filtro de ativo é gerenciado pelo @SQLRestriction da AuditableEntity.
     */
    @Query("SELECT c FROM Checklist c LEFT JOIN FETCH c.itens WHERE c.id = :id")
    Optional<Checklist> findByIdWithItens(@Param("id") Integer id);

    /**
     * Soft delete: marca checklist como inativo.
     */
    @Modifying
    @Query("UPDATE Checklist c SET c.ativo = false WHERE c.id = :id")
    void softDelete(@Param("id") Integer id);

    /**
     * Verifica se já existe checklist ativo com mesmo nome no projeto.
     */
    boolean existsByNomeAndProjetoIdAndAtivoTrue(String nome, Integer projetoId);

    /**
     * Verifica se já existe checklist com mesmo nome no projeto, excluindo um id específico.
     */
    boolean existsByNomeAndProjetoIdAndIdNot(String nome, Integer projetoId, Integer id);
}
