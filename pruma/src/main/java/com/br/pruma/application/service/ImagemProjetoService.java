package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ImagemProjetoRequestDTO;
import com.br.pruma.application.dto.response.ImagemProjetoResponseDTO;
import com.br.pruma.application.dto.update.ImagemProjetoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImagemProjetoService {
    ImagemProjetoResponseDTO create(ImagemProjetoRequestDTO dto);
    ImagemProjetoResponseDTO getById(Integer id);
    List<ImagemProjetoResponseDTO> listAll();
    Page<ImagemProjetoResponseDTO> list(Pageable pageable);
    List<ImagemProjetoResponseDTO> listByProjeto(Integer projetoId);
    ImagemProjetoResponseDTO update(Integer id, ImagemProjetoUpdateDTO dto);
    void delete(Integer id);
}
