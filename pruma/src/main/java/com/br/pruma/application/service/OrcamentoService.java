package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.OrcamentoRequestDTO;
import com.br.pruma.application.dto.response.OrcamentoResponseDTO;
import com.br.pruma.application.dto.update.OrcamentoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrcamentoService {
    OrcamentoResponseDTO create(OrcamentoRequestDTO dto);
    OrcamentoResponseDTO getById(Integer id);
    List<OrcamentoResponseDTO> listAll();
    Page<OrcamentoResponseDTO> list(Pageable pageable);
    List<OrcamentoResponseDTO> listByProjeto(Integer projetoId);
    OrcamentoResponseDTO update(Integer id, OrcamentoUpdateDTO dto);
    OrcamentoResponseDTO replace(Integer id, OrcamentoRequestDTO dto);
    void delete(Integer id);
}
