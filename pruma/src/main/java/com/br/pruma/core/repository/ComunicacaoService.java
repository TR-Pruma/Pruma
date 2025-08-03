package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Comunicacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface que define as operações de serviço para a entidade Comunicacao.
 */
public interface ComunicacaoService {

    /**
     * Cria uma nova comunicação.
     *
     * @param comunicacao Objeto contendo os dados da comunicação
     * @return A comunicação criada
     */
    Comunicacao criar(Comunicacao comunicacao);

    /**
     * Busca uma comunicação pelo ID.
     *
     * @param id ID da comunicação
     * @return A comunicação encontrada
     * @throws jakarta.persistence.EntityNotFoundException se não encontrar a comunicação
     */
    Comunicacao buscarPorId(Integer id);

    /**
     * Lista todas as comunicações ativas de um projeto de forma paginada.
     *
     * @param projetoId ID do projeto
     * @param pageable Objeto de paginação
     * @return Página com as comunicações do projeto
     */
    Page<Comunicacao> listarPorProjeto(Integer projetoId, Pageable pageable);

    /**
     * Lista todas as comunicações ativas de um cliente.
     *
     * @param clienteId ID do cliente
     * @return Lista com as comunicações do cliente
     */
    List<Comunicacao> listarPorCliente(Integer clienteId);

    /**
     * Lista todas as comunicações ativas entre um projeto e um cliente.
     *
     * @param projetoId ID do projeto
     * @param clienteId ID do cliente
     * @param pageable Objeto de paginação
     * @return Página com as comunicações entre o projeto e cliente
     */
    Page<Comunicacao> listarPorProjetoECliente(Integer projetoId, Integer clienteId, Pageable pageable);

    /**
     * Atualiza uma comunicação existente.
     *
     * @param id ID da comunicação a ser atualizada
     * @param comunicacao Objeto com os novos dados
     * @return A comunicação atualizada
     * @throws jakarta.persistence.EntityNotFoundException se não encontrar a comunicação
     */
    Comunicacao atualizar(Integer id, Comunicacao comunicacao);

    /**
     * Exclui logicamente uma comunicação.
     *
     * @param id ID da comunicação a ser excluída
     * @throws jakarta.persistence.EntityNotFoundException se não encontrar a comunicação
     */
    void deletar(Integer id);
}
