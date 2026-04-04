package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.SolicitacaoMudancaRequestDTO;
import com.br.pruma.application.dto.response.SolicitacaoMudancaResponseDTO;
import com.br.pruma.application.dto.update.SolicitacaoMudancaUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SolicitacaoMudancaService {
    SolicitacaoMudancaResponseDTO create(SolicitacaoMudancaRequestDTO dto);
    SolicitacaoMudancaResponseDTO getById(Integer id);
    List<SolicitacaoMudancaResponseDTO> listAll();
    Page<SolicitacaoMudancaResponseDTO> list(Pageable pageable);
    List<SolicitacaoMudancaResponseDTO> listByProjeto(Integer projetoId);
    SolicitacaoMudancaResponseDTO update(Integer id, SolicitacaoMudancaUpdateDTO dto);
    SolicitacaoMudancaResponseDTO replace(Integer id, SolicitacaoMudancaRequestDTO dto);
    void delete(Integer id);
}
