package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Logradouro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogradouroRepository extends JpaRepository<Logradouro, Integer> {

    /**
     * Pesquisa logradouro pelo tipo (ex: Rua, Avenida).
     */
    Optional<Logradouro> findByTipo(String tipo);

    /**
     * Lista todos os logradouros ordenados por tipo.
     */
    List<Logradouro> findAllByOrderByTipoAsc();
}

