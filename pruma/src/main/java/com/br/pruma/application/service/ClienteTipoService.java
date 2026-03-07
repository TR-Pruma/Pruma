package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ClienteTipoRequestDTO;
import com.br.pruma.application.dto.response.ClienteTipoResponseDTO;

import java.util.List;

public interface ClienteTipoService {
    ClienteTipoResponseDTO criar(ClienteTipoRequestDTO request);
    List<ClienteTipoResponseDTO> listarTodos();
    ClienteTipoResponseDTO buscarPorId(Integer id);
    ClienteTipoResponseDTO atualizar(Integer id, ClienteTipoRequestDTO request);
    void deletar(Integer id);
}
