package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.AnexoRequestDTO;
import com.br.pruma.application.dto.response.AnexoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnexoService {
    AnexoResponseDTO create(AnexoRequestDTO dto);
    AnexoResponseDTO getById(Integer id);
    List<AnexoResponseDTO> listAll();
    Page<AnexoResponseDTO> list(Pageable pageable);
    AnexoResponseDTO update(Integer id, AnexoRequestDTO dto);
    void delete(Integer id);
}
