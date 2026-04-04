package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ProjetoRequestDTO;
import com.br.pruma.application.dto.response.ProjetoResponseDTO;
import com.br.pruma.application.dto.update.ProjetoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjetoService {
    ProjetoResponseDTO create(ProjetoRequestDTO dto);
    ProjetoResponseDTO getById(Integer id);
    List<ProjetoResponseDTO> listAll();
    Page<ProjetoResponseDTO> list(Pageable pageable);
    List<ProjetoResponseDTO> listByCliente(Integer clienteId);
    ProjetoResponseDTO update(Integer id, ProjetoUpdateDTO dto);
    ProjetoResponseDTO replace(Integer id, ProjetoRequestDTO dto);
    void delete(Integer id);
}
