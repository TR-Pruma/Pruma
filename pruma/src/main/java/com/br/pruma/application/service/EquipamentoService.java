package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoResponseDTO;
import com.br.pruma.application.dto.update.EquipamentoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EquipamentoService {
    EquipamentoResponseDTO create(EquipamentoRequestDTO dto);
    EquipamentoResponseDTO getById(Integer id);
    List<EquipamentoResponseDTO> listAll();
    Page<EquipamentoResponseDTO> list(Pageable pageable);
    EquipamentoResponseDTO update(Integer id, EquipamentoUpdateDTO dto);
    EquipamentoResponseDTO replace(Integer id, EquipamentoRequestDTO dto);
    void delete(Integer id);
}
