package com.br.pruma.core.repository;

import com.br.pruma.core.domain.ComunicacaoAux;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComunicacaoAuxRepository extends JpaRepository<ComunicacaoAux, Integer> {

    // createdAt vem de AuditableEntity — não existe 'dataCriacao' em ComunicacaoAux
    Page<ComunicacaoAux> findByComunicacao_ProjetoIdAndAtivoTrueOrderByCreatedAtDesc(Integer projetoId, Pageable pageable);

    List<ComunicacaoAux> findByComunicacao_ClienteIdAndAtivoTrueOrderByCreatedAtDesc(Integer clienteId);

    Optional<ComunicacaoAux> findByComunicacaoIdAndAtivoTrue(Integer comunicacaoId);

    void deleteByComunicacaoId(Integer comunicacaoId);
}
