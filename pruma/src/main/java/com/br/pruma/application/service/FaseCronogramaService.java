package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.FaseCronogramaRequestDTO;
import com.br.pruma.application.dto.response.FaseCronogramaResponseDTO;
import com.br.pruma.application.dto.update.FaseCronogramaUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FaseCronogramaService {
    FaseCronogramaResponseDTO create(FaseCronogramaRequestDTO dto);
    FaseCronogramaResponseDTO getById(Integer id);
    List<FaseCronogramaResponseDTO> listAll();
    Page<FaseCronogramaResponseDTO> list(Pageable pageable);
    List<FaseCronogramaResponseDTO> listByCronograma(Integer cronogramaId);
    FaseCronogramaResponseDTO update(Integer id, FaseCronogramaUpdateDTO dto);
    FaseCronogramaResponseDTO replace(Integer id, FaseCronogramaRequestDTO dto);
    void delete(Integer id);
}
