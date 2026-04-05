package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.MaterialRequestDTO;
import com.br.pruma.application.dto.response.MaterialResponseDTO;
import com.br.pruma.application.dto.update.MaterialUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialService {
    MaterialResponseDTO create(MaterialRequestDTO dto);
    MaterialResponseDTO getById(Integer id);
    List<MaterialResponseDTO> listAll();
    Page<MaterialResponseDTO> list(Pageable pageable);
    MaterialResponseDTO update(Integer id, MaterialUpdateDTO dto);
    MaterialResponseDTO replace(Integer id, MaterialRequestDTO dto);
    void delete(Integer id);
}
