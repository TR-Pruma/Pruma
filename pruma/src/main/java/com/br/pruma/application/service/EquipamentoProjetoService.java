package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.EquipamentoProjetoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoProjetoResponseDTO;
import com.br.pruma.application.dto.update.EquipamentoProjetoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EquipamentoProjetoService {
    EquipamentoProjetoResponseDTO create(EquipamentoProjetoRequestDTO dto);
    EquipamentoProjetoResponseDTO getById(Integer id);
    List<EquipamentoProjetoResponseDTO> listAll();
    Page<EquipamentoProjetoResponseDTO> list(Pageable pageable);
    List<EquipamentoProjetoResponseDTO> listByProjeto(Integer projetoId);
    EquipamentoProjetoResponseDTO update(Integer id, EquipamentoProjetoUpdateDTO dto);
    void delete(Integer id);
}
