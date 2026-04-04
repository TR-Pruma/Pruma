package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.LogradouroRequestDTO;
import com.br.pruma.application.dto.response.LogradouroResponseDTO;
import com.br.pruma.application.dto.update.LogradouroUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LogradouroService {
    LogradouroResponseDTO create(LogradouroRequestDTO dto);
    LogradouroResponseDTO getById(Integer id);
    List<LogradouroResponseDTO> listAll();
    Page<LogradouroResponseDTO> list(Pageable pageable);
    LogradouroResponseDTO update(Integer id, LogradouroUpdateDTO dto);
    LogradouroResponseDTO replace(Integer id, LogradouroRequestDTO dto);
    void delete(Integer id);
}
