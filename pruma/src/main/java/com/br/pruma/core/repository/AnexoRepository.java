package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Anexo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AnexoRepository extends JpaRepository<Anexo, Integer> {

    // Busca apenas registros ativos
    Optional<Anexo> findByIdAndAtivoTrue(Integer id);

    List<Anexo> findAllByProjetoIdAndAtivoTrue(Integer projetoId);

    Page<Anexo> findAllByAtivoTrue(Pageable pageable);

    // Soft delete — seta ativo = false em vez de apagar
    @Modifying
    @Transactional
    @Query("UPDATE Anexo a SET a.ativo = false WHERE a.id = :id")
    void softDelete(@Param("id") Integer id);
}
