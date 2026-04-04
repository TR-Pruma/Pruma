package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.TipoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.TipoUsuarioResponseDTO;
import com.br.pruma.application.dto.update.TipoUsuarioUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TipoUsuarioService {
    TipoUsuarioResponseDTO create(TipoUsuarioRequestDTO dto);
    TipoUsuarioResponseDTO getById(Integer id);
    List<TipoUsuarioResponseDTO> listAll();
    Page<TipoUsuarioResponseDTO> list(Pageable pageable);
    TipoUsuarioResponseDTO update(Integer id, TipoUsuarioUpdateDTO dto);
    TipoUsuarioResponseDTO replace(Integer id, TipoUsuarioRequestDTO dto);
    void delete(Integer id);
}
