package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.LogAlteracaoRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoResponseDTO;
import com.br.pruma.application.dto.update.LogAlteracaoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LogAlteracaoService {
    LogAlteracaoResponseDTO create(LogAlteracaoRequestDTO dto);
    LogAlteracaoResponseDTO getById(Integer id);
    List<LogAlteracaoResponseDTO> listAll();
    Page<LogAlteracaoResponseDTO> list(Pageable pageable);
    List<LogAlteracaoResponseDTO> listByProjeto(Integer projetoId);
    LogAlteracaoResponseDTO update(Integer id, LogAlteracaoUpdateDTO dto);
    void delete(Integer id);
}
