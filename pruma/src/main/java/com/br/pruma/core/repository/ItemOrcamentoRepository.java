package com.br.pruma.core.repository;

import com.br.pruma.core.domain.ItemOrcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemOrcamentoRepository extends JpaRepository<ItemOrcamento, Integer> {

    /**
     * Recupera todos os itens vinculados a um orçamento específico.
     */
    List<ItemOrcamento> findByOrcamento_Id(Integer orcamentoId);

}
