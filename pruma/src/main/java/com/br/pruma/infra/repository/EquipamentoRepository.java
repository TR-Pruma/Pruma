package com.br.pruma.infra.repository;

import com.br.pruma.core.domain.Equipamento;
import com.br.pruma.core.enums.StatusEquipamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer>,
                                               JpaSpecificationExecutor<Equipamento>,
                                               EquipamentoRepositoryCustom {

    // ===== Operações padrão (já consideram ativo = true por causa do @Where) =====

    Optional<Equipamento> findById(Integer id);

    boolean existsByNomeIgnoreCase(String nome);

    Page<Equipamento> findAllByStatus(StatusEquipamento status, Pageable pageable);

    Page<Equipamento> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    long countByStatus(StatusEquipamento status);

    // ===== Cenários especiais (explicitamente nomeados) =====

    /**
     * Busca por ID incluindo registros inativos (bypassa o @Where usando native query).
     */
    @Query(value = "SELECT * FROM equipamento WHERE equipamento_id = :id", nativeQuery = true)
    Optional<Equipamento> findByIdIncludingInativos(@Param("id") Integer id);

    /**
     * Lista equipamentos (ativos e inativos) com paginação simples por status (native query para bypass do @Where).
     */
    @Query(
            value = "SELECT * FROM equipamento WHERE (:status IS NULL OR status = :status) ORDER BY equipamento_id DESC",
            countQuery = "SELECT COUNT(*) FROM equipamento WHERE (:status IS NULL OR status = :status)",
            nativeQuery = true
    )
    Page<Equipamento> findAllIncludingInativosByStatus(@Param("status") String status, Pageable pageable);

    /**
     * Hard delete consciente (use com parcimônia).
     * Observação: este método ignora o @SQLDelete e remove fisicamente.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM Equipamento e WHERE e.id = :id")
    void hardDeleteById(@Param("id") Integer id);

    /**
     * Inativação (soft delete) explícita via update – alternativa ao delete() do JPA.
     * Útil quando você quer evitar o cascade de deleção ou eventos de entidade.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Equipamento e SET e.ativo = false WHERE e.id = :id")
    void softDeleteById(@Param("id") Integer id);

    /**
     * Reativação de um registro previamente inativado.
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Equipamento e SET e.ativo = true WHERE e.id = :id")
    void reativarById(@Param("id") Integer id);

    // ===== Projeções leves (performance em listas e combos) =====

    interface IdNomeProjection {
        Integer getId();
        String getNome();
    }

    List<IdNomeProjection> findByStatus(StatusEquipamento status);
}
