package com.br.pruma.core.repository;

import com.br.pruma.application.dto.request.ComunicacaoAuxRequestDTO;
import com.br.pruma.application.dto.response.ComunicacaoAuxResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ComunicacaoAuxService {
    ComunicacaoAuxResponseDTO criar(ComunicacaoAuxRequestDTO request);
    ComunicacaoAuxResponseDTO buscarPorComunicacaoId(Integer comunicacaoId);
    Page<ComunicacaoAuxResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable);
    List<ComunicacaoAuxResponseDTO> listarPorCliente(Integer clienteId);
    void deletar(Integer comunicacaoId);
}
