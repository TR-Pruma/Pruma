package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.EnderecoRequestDTO;
import com.br.pruma.application.dto.response.EnderecoResponseDTO;

import java.util.List;

public interface EnderecoService {
    EnderecoResponseDTO salvar(EnderecoRequestDTO dto);
    EnderecoResponseDTO buscarPorId(Integer id);
    List<EnderecoResponseDTO> listarTodos();
    EnderecoResponseDTO atualizar(Integer id, EnderecoRequestDTO dto);
    void deletar(Integer id);
}
