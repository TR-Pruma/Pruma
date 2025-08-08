package com.br.pruma.infra.repository;

import com.br.pruma.core.domain.Comunicacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComunicacaoService {
    Comunicacao criar(Comunicacao comunicacao);
    Comunicacao buscarPorId(Integer id);
    Page<Comunicacao> listarPorProjeto(Integer projetoId, Pageable pageable);
    List<Comunicacao> listarPorCliente(Integer clienteId);
    Page<Comunicacao> listarPorProjetoECliente(Integer projetoId, Integer clienteId, Pageable pageable);
    void deletar(Integer id);
}
