package com.br.pruma.core.repository;


import com.br.pruma.core.domain.StatusEquipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusEquipamentoRepository extends JpaRepository<StatusEquipamento, Integer> {

    /**
     * Busca status de equipamento por descrição.
     */
    Optional<StatusEquipamento> findByDescricao(String descricao);
}
