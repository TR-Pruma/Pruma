package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.RelatorioRequestDTO;
import com.br.pruma.application.dto.response.RelatorioResponseDTO;
import com.br.pruma.application.dto.update.RelatorioUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RelatorioService {
    RelatorioResponseDTO create(RelatorioRequestDTO dto);
    RelatorioResponseDTO getById(Integer id);
    List<RelatorioResponseDTO> listAll();
    Page<RelatorioResponseDTO> list(Pageable pageable);
    List<RelatorioResponseDTO> listByProjeto(Integer projetoId);
    RelatorioResponseDTO update(Integer id, RelatorioUpdateDTO dto);
    RelatorioResponseDTO replace(Integer id, RelatorioRequestDTO dto);
    void delete(Integer id);
}
