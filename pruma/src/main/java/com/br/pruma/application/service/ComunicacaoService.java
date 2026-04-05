package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ComunicacaoRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface que define as operações de serviço para a entidade Comunicacao.
 */
public interface ComunicacaoService {

    ComunicacaoResponseDTO criar(ComunicacaoRequestDTO dto);

    ComunicacaoResponseDTO buscarPorId(Integer id);

    Page<ComunicacaoResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable);

    List<ComunicacaoResponseDTO> listarPorCliente(Integer clienteId);

    Page<ComunicacaoResponseDTO> listarPorProjetoECliente(Integer projetoId, Integer clienteId, Pageable pageable);

    ComunicacaoResponseDTO atualizar(Integer id, ComunicacaoRequestDTO dto);

    void deletar(Integer id);
}
