package com.br.pruma.infra.repository;

import com.br.pruma.core.domain.ProjetoProfissional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoProfissionalRepository extends JpaRepository<ProjetoProfissional, Integer> {

    /**
     * Busca todas as associações de um projeto específico.
     */
    List<ProjetoProfissional> findByProjetoId(Integer projetoId);

    /**
     * Busca todas as associações de um profissional específico.
     */
    List<ProjetoProfissional> findByProfissionalId(Integer profissionalId);

    /**
     * Paginação geral (já fornecida por JpaRepository#findAll(Pageable)),
     * mas deixo o método aqui apenas como referência de uso no service.
     */
    Page<ProjetoProfissional> findAll(Pageable pageable);
}

