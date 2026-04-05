package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.InspecaoRequestDTO;
import com.br.pruma.application.dto.response.InspecaoResponseDTO;
import com.br.pruma.application.dto.update.InspecaoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InspecaoService {
    InspecaoResponseDTO create(InspecaoRequestDTO dto);
    InspecaoResponseDTO getById(Integer id);
    List<InspecaoResponseDTO> listAll();
    Page<InspecaoResponseDTO> list(Pageable pageable);
    List<InspecaoResponseDTO> listByProjeto(Integer projetoId);
    InspecaoResponseDTO update(Integer id, InspecaoUpdateDTO dto);
    InspecaoResponseDTO replace(Integer id, InspecaoRequestDTO dto);
    void delete(Integer id);
}
