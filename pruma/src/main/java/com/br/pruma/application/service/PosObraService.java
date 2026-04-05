package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.PosObraRequestDTO;
import com.br.pruma.application.dto.response.PosObraResponseDTO;
import com.br.pruma.application.dto.update.PosObraUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PosObraService {
    PosObraResponseDTO create(PosObraRequestDTO dto);
    PosObraResponseDTO getById(Integer id);
    List<PosObraResponseDTO> listAll();
    Page<PosObraResponseDTO> list(Pageable pageable);
    List<PosObraResponseDTO> listByProjeto(Integer projetoId);
    PosObraResponseDTO update(Integer id, PosObraUpdateDTO dto);
    PosObraResponseDTO replace(Integer id, PosObraRequestDTO dto);
    void delete(Integer id);
}
