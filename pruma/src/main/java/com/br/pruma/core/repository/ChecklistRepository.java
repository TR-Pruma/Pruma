package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {

    @Query("SELECT c FROM Checklist c WHERE c.projeto.id = :projetoId")

    @Query("SELECT c FROM Checklist c LEFT JOIN FETCH c.itens i LEFT JOIN FETCH c.projeto WHERE c.id = :id")




    @Query("SELECT c FROM Checklist c LEFT JOIN FETCH c.itens WHERE c.projeto.id = :projetoId ORDER BY c.dataCriacao DESC")
    List<Checklist> findByProjetoIdWithItens(@Param("projetoId") Integer projetoId);

    @Query("SELECT COUNT(c) > 0 FROM Checklist c WHERE c.nome = :nome AND c.projeto.id = :projetoId AND c.id <> :checklistId")
    boolean existsByNomeAndProjetoIdAndIdNot(@Param("nome") String nome, @Param("projetoId") Integer projetoId, @Param("checklistId") Integer checklistId);
    boolean existsByNomeAndProjetoIdAndAtivoTrue(String nome, Integer projetoId);

    @Modifying
    @Query("UPDATE Checklist c SET c.ativo = false WHERE c.id = :id")
    void softDelete(@Param("id") Integer id);

    List<Checklist> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);
}
