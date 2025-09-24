package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {

    /**
     * Busca Checklist por id trazendo projeto e itens (evita n+1).
     */
    @Query("SELECT c FROM Checklist c LEFT JOIN FETCH c.itens i LEFT JOIN FETCH c.projeto p WHERE c.id = :id")
    Optional<Checklist> findByIdWithItens(@Param("id") Integer id);

    /**
     * Lista checklists de um projeto trazendo itens (ordenado por data de criação descendente).
     */
    @Query("SELECT DISTINCT c FROM Checklist c LEFT JOIN FETCH c.itens WHERE c.projeto.id = :projetoId ORDER BY c.dataCriacao DESC")
    List<Checklist> findByProjetoIdWithItens(@Param("projetoId") Integer projetoId);

    /**
     * Verifica existência de outro checklist com mesmo nome no projeto (exclui o próprio id).
     */
    @Query("SELECT COUNT(c) > 0 FROM Checklist c WHERE c.nome = :nome AND c.projeto.id = :projetoId AND c.id <> :checklistId")
    boolean existsByNomeAndProjetoIdAndIdNot(@Param("nome") String nome,
                                             @Param("projetoId") Integer projetoId,
                                             @Param("checklistId") Integer checklistId);

    /**
     * Verifica existência de checklist ativo com mesmo nome no projeto.
     */
    boolean existsByNomeAndProjetoIdAndAtivoTrue(String nome, Integer projetoId);

    /**
     * Soft delete (marca ativo = false).
     */
    @Modifying
    @Transactional
    @Query("UPDATE Checklist c SET c.ativo = false WHERE c.id = :id")
    void softDelete(@Param("id") Integer id);

    /**
     * Busca checklists ativos cujo nome contenha o texto informado (case-insensitive).
     */
    List<Checklist> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);
}
