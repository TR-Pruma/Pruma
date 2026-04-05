package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.PreObraRequestDTO;
import com.br.pruma.application.dto.response.PreObraResponseDTO;
import com.br.pruma.application.dto.update.PreObraUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PreObraService {
    PreObraResponseDTO create(PreObraRequestDTO dto);
    PreObraResponseDTO getById(Integer id);
    List<PreObraResponseDTO> listAll();
    Page<PreObraResponseDTO> list(Pageable pageable);
    List<PreObraResponseDTO> listByProjeto(Integer projetoId);
    PreObraResponseDTO update(Integer id, PreObraUpdateDTO dto);
    PreObraResponseDTO replace(Integer id, PreObraRequestDTO dto);
    void delete(Integer id);
}
