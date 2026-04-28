package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.StatusEquipamentoRequestDTO;
import com.br.pruma.application.dto.response.StatusEquipamentoResponseDTO;
import com.br.pruma.application.dto.update.StatusEquipamentoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StatusEquipamentoService {
    StatusEquipamentoResponseDTO create(StatusEquipamentoRequestDTO dto);
    StatusEquipamentoResponseDTO getById(Integer id);
    List<StatusEquipamentoResponseDTO> listAll();
    Page<StatusEquipamentoResponseDTO> list(Pageable pageable);
    StatusEquipamentoResponseDTO update(Integer id, StatusEquipamentoUpdateDTO dto);
    StatusEquipamentoResponseDTO replace(Integer id, StatusEquipamentoRequestDTO dto);
    void delete(Integer id);
}
