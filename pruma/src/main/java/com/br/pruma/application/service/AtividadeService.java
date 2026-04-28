package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.AtividadeRequestDTO;
import com.br.pruma.application.dto.response.AtividadeResponseDTO;
import com.br.pruma.application.dto.update.AtividadeUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AtividadeService {
    AtividadeResponseDTO create(AtividadeRequestDTO dto);
    AtividadeResponseDTO getById(Integer id);
    List<AtividadeResponseDTO> listAll();
    Page<AtividadeResponseDTO> list(Pageable pageable);
    AtividadeResponseDTO update(Integer id, AtividadeUpdateDTO dto);
    AtividadeResponseDTO replace(Integer id, AtividadeRequestDTO dto);
    void delete(Integer id);
}
