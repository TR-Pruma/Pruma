package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ComunicacaoAuxRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoAuxResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComunicacaoAuxService {

    ComunicacaoAuxResponseDTO criar(ComunicacaoAuxRequestDTO dto);

    ComunicacaoAuxResponseDTO buscarPorComunicacaoId(Integer comunicacaoId);

    ComunicacaoAuxResponseDTO buscarPorId(Integer id);

    Page<ComunicacaoAuxResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable);

    List<ComunicacaoAuxResponseDTO> listarPorCliente(Integer clienteId);

    void deletar(Integer id);
}
