package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.HistoricoLocalizacaoRequestDTO;
import com.br.pruma.application.dto.response.HistoricoLocalizacaoResponseDTO;

import java.util.List;

public interface HistoricoLocalizacaoService {
    HistoricoLocalizacaoResponseDTO salvar(HistoricoLocalizacaoRequestDTO dto);
    List<HistoricoLocalizacaoResponseDTO> listarTodos();
    HistoricoLocalizacaoResponseDTO buscarPorId(Integer id);
    void deletar(Integer id);
}