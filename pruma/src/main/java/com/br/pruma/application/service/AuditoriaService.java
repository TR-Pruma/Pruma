package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.AuditoriaRequestDTO;
import com.br.pruma.application.dto.response.AuditoriaResponseDTO;
import com.br.pruma.application.dto.update.AuditoriaUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuditoriaService {
    AuditoriaResponseDTO create(AuditoriaRequestDTO dto);
    AuditoriaResponseDTO getById(Integer id);
    List<AuditoriaResponseDTO> listAll();
    Page<AuditoriaResponseDTO> list(Pageable pageable);
    AuditoriaResponseDTO update(Integer id, AuditoriaUpdateDTO dto);
    void delete(Integer id);
}
