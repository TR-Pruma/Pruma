package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.ItemOrcamento;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de ItemOrcamento.
 * Define o contrato que a camada de aplicação usa sem depender
 * diretamente de JPA ou de qualquer tecnologia de persistência.
 */
public interface ItemOrcamentoRepositoryPort {

    /**
     * Persiste ou atualiza um item de orçamento.
     */
    ItemOrcamento save(ItemOrcamento itemOrcamento);

    /**
     * Busca um item de orçamento pelo seu ID.
     */
    Optional<ItemOrcamento> findById(Integer id);

    /**
     * Retorna todos os itens de orçamento.
     */
    List<ItemOrcamento> findAll();

    /**
     * Retorna todos os itens vinculados a um orçamento específico.
     */
    List<ItemOrcamento> findByOrcamentoId(Integer orcamentoId);

    /**
     * Remove um item de orçamento.
     */
    void delete(ItemOrcamento itemOrcamento);

    /**
     * Verifica se um item de orçamento existe pelo seu ID.
     */
    boolean existsById(Integer id);
}
