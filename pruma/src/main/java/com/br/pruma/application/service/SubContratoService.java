package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.SubContratoRequestDTO;
import com.br.pruma.application.dto.response.SubContratoResponseDTO;
import com.br.pruma.application.dto.update.SubContratoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubContratoService {
    SubContratoResponseDTO create(SubContratoRequestDTO dto);
    SubContratoResponseDTO getById(Integer id);
    List<SubContratoResponseDTO> listAll();
    Page<SubContratoResponseDTO> list(Pageable pageable);
    List<SubContratoResponseDTO> listByProjeto(Integer projetoId);
    SubContratoResponseDTO update(Integer id, SubContratoUpdateDTO dto);
    SubContratoResponseDTO replace(Integer id, SubContratoRequestDTO dto);
    void delete(Integer id);
}
