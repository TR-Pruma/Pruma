package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.NotificacaoRequestDTO;
import com.br.pruma.application.dto.response.NotificacaoResponseDTO;
import com.br.pruma.application.dto.update.NotificacaoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificacaoService {
    NotificacaoResponseDTO create(NotificacaoRequestDTO dto);
    NotificacaoResponseDTO getById(Integer id);
    List<NotificacaoResponseDTO> listAll();
    Page<NotificacaoResponseDTO> list(Pageable pageable);
    List<NotificacaoResponseDTO> listByUsuario(Integer usuarioId);
    NotificacaoResponseDTO marcarComoLida(Integer id);
    NotificacaoResponseDTO update(Integer id, NotificacaoUpdateDTO dto);
    void delete(Integer id);
}
