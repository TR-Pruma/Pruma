package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.PermissaoUsuarioRequestDTO;
import com.br.pruma.application.dto.response.PermissaoUsuarioResponseDTO;
import com.br.pruma.application.dto.update.PermissaoUsuarioUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PermissaoUsuarioService {
    PermissaoUsuarioResponseDTO create(PermissaoUsuarioRequestDTO dto);
    PermissaoUsuarioResponseDTO getById(Long id);
    List<PermissaoUsuarioResponseDTO> listAll();
    Page<PermissaoUsuarioResponseDTO> list(Pageable pageable);
    List<PermissaoUsuarioResponseDTO> listByClienteCpf(String cpf);
    PermissaoUsuarioResponseDTO update(Long id, PermissaoUsuarioUpdateDTO dto);
    void delete(Long id);
}
