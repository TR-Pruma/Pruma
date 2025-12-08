package com.br.pruma.core.repository;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {
    ClienteResponseDTO create(ClienteRequestDTO dto);
    ClienteResponseDTO update(Integer id, ClienteRequestDTO dto);
    ClienteResponseDTO findById(Integer id);
    List<ClienteResponseDTO> findAll();
    void delete(Integer id);

}
