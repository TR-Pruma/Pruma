package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import com.br.pruma.application.dto.update.CronogramaUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CronogramaService {
    CronogramaResponseDTO create(CronogramaRequestDTO dto);
    CronogramaResponseDTO getById(Integer id);
    List<CronogramaResponseDTO> listAll();
    Page<CronogramaResponseDTO> list(Pageable pageable);
    List<CronogramaResponseDTO> listByProjeto(Integer projetoId);
    CronogramaResponseDTO update(Integer id, CronogramaUpdateDTO dto);
    CronogramaResponseDTO replace(Integer id, CronogramaRequestDTO dto);
    void delete(Integer id);
}
