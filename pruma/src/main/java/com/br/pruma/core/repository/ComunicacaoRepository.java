package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Comunicacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComunicacaoRepository extends JpaRepository<Comunicacao, Integer> {
    Optional<Comunicacao> findByIdAndAtivoTrue(Integer id);

    Page<Comunicacao> findByProjetoIdAndAtivoTrueOrderByCreatedAtDesc(
        Integer projetoId,
        Pageable pageable
    );

    List<Comunicacao> findByClienteIdAndAtivoTrueOrderByCreatedAtDesc(
        Integer clienteId
    );

    Page<Comunicacao> findByProjetoIdAndClienteIdAndAtivoTrueOrderByCreatedAtDesc(
        Integer projetoId,
        Integer clienteId,
        Pageable pageable
    );

    boolean existsByProjetoIdAndClienteIdAndAtivoTrue(
        Integer projetoId,
        Integer clienteId
    );
}
