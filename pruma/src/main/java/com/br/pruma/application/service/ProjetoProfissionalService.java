package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ProjetoProfissionalRequestDTO;
import com.br.pruma.application.dto.response.ProjetoProfissionalResponseDTO;
import com.br.pruma.application.dto.update.ProjetoProfissionalUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjetoProfissionalService {
    ProjetoProfissionalResponseDTO create(ProjetoProfissionalRequestDTO dto);
    ProjetoProfissionalResponseDTO getById(Integer id);
    List<ProjetoProfissionalResponseDTO> listAll();
    Page<ProjetoProfissionalResponseDTO> list(Pageable pageable);
    List<ProjetoProfissionalResponseDTO> listByProjeto(Integer projetoId);
    ProjetoProfissionalResponseDTO update(Integer id, ProjetoProfissionalUpdateDTO dto);
    void delete(Integer id);
}
