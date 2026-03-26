package com.br.pruma.application.service;


import com.br.pruma.core.domain.ComunicacaoAux;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComunicacaoAuxService {

    /**
     * Cria uma nova comunicação auxiliar.
     */
    ComunicacaoAux criar(ComunicacaoAux entity);

    /**
     * Busca comunicação auxiliar pelo ID da comunicação principal.
     */
    ComunicacaoAux buscarPorComunicacaoId(Integer comunicacaoId);

    /**
     * Busca comunicação auxiliar pelo seu próprio ID.
     */
    ComunicacaoAux buscarPorId(Integer id);

    /**
     * Lista comunicações auxiliares de um projeto.
     */
    Page<ComunicacaoAux> listarPorProjeto(Integer projetoId, Pageable pageable);

    /**
     * Lista comunicações auxiliares de um cliente.
     */
    List<ComunicacaoAux> listarPorCliente(Integer clienteId);

    /**
     * Deleta uma comunicação auxiliar pelo ID.
     */
    void deletar(Integer id);
}
