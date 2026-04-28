package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ChecklistRequestDTO;
import com.br.pruma.application.dto.response.ChecklistResponseDTO;
import com.br.pruma.application.dto.update.ChecklistUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChecklistService {
    ChecklistResponseDTO create(ChecklistRequestDTO dto);
    ChecklistResponseDTO getById(Integer id);
    List<ChecklistResponseDTO> listAll();
    Page<ChecklistResponseDTO> list(Pageable pageable);
    List<ChecklistResponseDTO> listByProjeto(Integer projetoId);
    ChecklistResponseDTO update(Integer id, ChecklistUpdateDTO dto);
    ChecklistResponseDTO replace(Integer id, ChecklistRequestDTO dto);
    void delete(Integer id);
}
