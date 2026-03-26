package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.InsumoRequestDTO;
import com.br.pruma.application.dto.response.InsumoResponseDTO;

import java.util.List;

public interface InsumoService {
    InsumoResponseDTO create(InsumoRequestDTO dto);
    InsumoResponseDTO getById(Integer id);
    List<InsumoResponseDTO> getAll();
    InsumoResponseDTO update(Integer id, InsumoRequestDTO dto);
    void delete(Integer id);

}
