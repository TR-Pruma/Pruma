package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.StatusSolicitacaoRequestDTO;
import com.br.pruma.application.dto.response.StatusSolicitacaoResponseDTO;
import com.br.pruma.application.dto.update.StatusSolicitacaoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StatusSolicitacaoService {
    StatusSolicitacaoResponseDTO create(StatusSolicitacaoRequestDTO dto);
    StatusSolicitacaoResponseDTO getById(Integer id);
    List<StatusSolicitacaoResponseDTO> listAll();
    Page<StatusSolicitacaoResponseDTO> list(Pageable pageable);
    StatusSolicitacaoResponseDTO update(Integer id, StatusSolicitacaoUpdateDTO dto);
    StatusSolicitacaoResponseDTO replace(Integer id, StatusSolicitacaoRequestDTO dto);
    void delete(Integer id);
}
