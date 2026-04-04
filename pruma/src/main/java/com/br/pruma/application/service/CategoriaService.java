package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.CategoriaRequestDTO;
import com.br.pruma.application.dto.response.CategoriaResponseDTO;
import com.br.pruma.application.dto.update.CategoriaUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoriaService {
    CategoriaResponseDTO create(CategoriaRequestDTO dto);
    CategoriaResponseDTO getById(Integer id);
    List<CategoriaResponseDTO> listAll();
    Page<CategoriaResponseDTO> list(Pageable pageable);
    CategoriaResponseDTO update(Integer id, CategoriaUpdateDTO dto);
    CategoriaResponseDTO replace(Integer id, CategoriaRequestDTO dto);
    void delete(Integer id);
}
