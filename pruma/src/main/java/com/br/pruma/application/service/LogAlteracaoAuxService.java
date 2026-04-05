package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.LogAlteracaoAuxRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoAuxResponseDTO;
import com.br.pruma.application.dto.update.LogAlteracaoAuxUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LogAlteracaoAuxService {
    LogAlteracaoAuxResponseDTO create(LogAlteracaoAuxRequestDTO dto);
    LogAlteracaoAuxResponseDTO getById(Integer id);
    List<LogAlteracaoAuxResponseDTO> listAll();
    Page<LogAlteracaoAuxResponseDTO> list(Pageable pageable);
    LogAlteracaoAuxResponseDTO update(Integer id, LogAlteracaoAuxUpdateDTO dto);
    void delete(Integer id);
}
