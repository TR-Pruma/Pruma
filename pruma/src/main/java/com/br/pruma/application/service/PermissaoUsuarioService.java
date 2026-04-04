package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.PermissaoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.PermissaoUsuarioResponseDTO;
import com.br.pruma.application.dto.update.PermissaoUsuarioUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PermissaoUsuarioService {
    PermissaoUsuarioResponseDTO create(PermissaoUsuarioRequestDTO dto);
    PermissaoUsuarioResponseDTO getById(Integer id);
    List<PermissaoUsuarioResponseDTO> listAll();
    Page<PermissaoUsuarioResponseDTO> list(Pageable pageable);
    List<PermissaoUsuarioResponseDTO> listByUsuario(Integer usuarioId);
    PermissaoUsuarioResponseDTO update(Integer id, PermissaoUsuarioUpdateDTO dto);
    void delete(Integer id);
}
