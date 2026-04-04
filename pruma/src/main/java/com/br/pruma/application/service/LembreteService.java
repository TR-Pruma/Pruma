package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.LembreteRequestDTO;
import com.br.pruma.application.dto.response.LembreteResponseDTO;
import com.br.pruma.application.dto.update.LembreteUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LembreteService {
    LembreteResponseDTO create(LembreteRequestDTO dto);
    LembreteResponseDTO getById(Integer id);
    List<LembreteResponseDTO> listAll();
    Page<LembreteResponseDTO> list(Pageable pageable);
    List<LembreteResponseDTO> listByProjeto(Integer projetoId);
    LembreteResponseDTO update(Integer id, LembreteUpdateDTO dto);
    LembreteResponseDTO replace(Integer id, LembreteRequestDTO dto);
    void delete(Integer id);
}
