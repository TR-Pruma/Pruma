package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ProjetoCategoriaRequestDTO;
import com.br.pruma.application.dto.response.ProjetoCategoriaResponseDTO;
import com.br.pruma.application.dto.update.ProjetoCategoriaUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjetoCategoriaService {
    ProjetoCategoriaResponseDTO create(ProjetoCategoriaRequestDTO dto);
    ProjetoCategoriaResponseDTO getById(Integer id);
    List<ProjetoCategoriaResponseDTO> listAll();
    Page<ProjetoCategoriaResponseDTO> list(Pageable pageable);
    List<ProjetoCategoriaResponseDTO> listByProjeto(Integer projetoId);
    ProjetoCategoriaResponseDTO update(Integer id, ProjetoCategoriaUpdateDTO dto);
    void delete(Integer id);
}
