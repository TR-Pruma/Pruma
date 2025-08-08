package com.br.pruma.infra.repository;

import com.br.pruma.core.domain.ComunicacaoAux;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComunicacaoAuxService {
    ComunicacaoAux criar(ComunicacaoAux comunicacaoAux);
    ComunicacaoAux buscarPorId(Integer comunicacaoId);
    Page<ComunicacaoAux> listarPorProjeto(Integer projetoId, Pageable pageable);
    List<ComunicacaoAux> listarPorCliente(Integer clienteId);
    void deletar(Integer comunicacaoId);
    Object buscarPorComunicacaoId(Integer comunicacaoId);
}
