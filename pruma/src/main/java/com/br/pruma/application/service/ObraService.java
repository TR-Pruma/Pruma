package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ObraRequestDTO;
import com.br.pruma.application.dto.response.ObraResponseDTO;
import com.br.pruma.application.dto.update.ObraUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ObraService {
    ObraResponseDTO create(ObraRequestDTO dto);
    ObraResponseDTO getById(Integer id);
    List<ObraResponseDTO> listAll();
    Page<ObraResponseDTO> list(Pageable pageable);
    List<ObraResponseDTO> listByProjeto(Integer projetoId);
    ObraResponseDTO update(Integer id, ObraUpdateDTO dto);
    ObraResponseDTO replace(Integer id, ObraRequestDTO dto);
    Page<ObraResponseDTO> searchByDescricao(String descricao, Pageable pageable);
    void delete(Integer id);
}
