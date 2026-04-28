package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.MaterialUtilizadoRequestDTO;
import com.br.pruma.application.dto.response.MaterialUtilizadoResponseDTO;
import com.br.pruma.application.dto.update.MaterialUtilizadoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialUtilizadoService {
    MaterialUtilizadoResponseDTO create(MaterialUtilizadoRequestDTO dto);
    MaterialUtilizadoResponseDTO getById(Integer id);
    List<MaterialUtilizadoResponseDTO> listAll();
    Page<MaterialUtilizadoResponseDTO> list(Pageable pageable);
    List<MaterialUtilizadoResponseDTO> listByProjeto(Integer projetoId);
    MaterialUtilizadoResponseDTO update(Integer id, MaterialUtilizadoUpdateDTO dto);
    MaterialUtilizadoResponseDTO replace(Integer id, MaterialUtilizadoRequestDTO dto);
    void delete(Integer id);
}
