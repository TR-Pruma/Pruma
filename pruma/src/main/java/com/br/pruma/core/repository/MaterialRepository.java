package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    /**
     * Busca material pelo texto exato da descrição.
     */
    Optional<Material> findByDescricao(String descricao);

    /**
     * Lista todos os materiais ordenados alfabeticamente pela descrição.
     */
    List<Material> findAllByOrderByDescricaoAsc();
}

