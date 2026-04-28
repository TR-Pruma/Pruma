package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ProfissionalDeBaseRequestDTO;
import com.br.pruma.application.dto.response.ProfissionalDeBaseResponseDTO;
import com.br.pruma.application.dto.update.ProfissionalDeBaseUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProfissionalDeBaseService {
    ProfissionalDeBaseResponseDTO create(ProfissionalDeBaseRequestDTO dto);
    ProfissionalDeBaseResponseDTO getById(Integer id);
    List<ProfissionalDeBaseResponseDTO> listAll();
    Page<ProfissionalDeBaseResponseDTO> list(Pageable pageable);
    ProfissionalDeBaseResponseDTO update(Integer id, ProfissionalDeBaseUpdateDTO dto);
    ProfissionalDeBaseResponseDTO replace(Integer id, ProfissionalDeBaseRequestDTO dto);
    void delete(Integer id);
}
