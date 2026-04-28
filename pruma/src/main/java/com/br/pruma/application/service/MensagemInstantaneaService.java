package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.MensagemInstantaneaRequestDTO;
import com.br.pruma.application.dto.response.MensagemInstantaneaResponseDTO;
import com.br.pruma.application.dto.update.MensagemInstantaneaUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MensagemInstantaneaService {
    MensagemInstantaneaResponseDTO create(MensagemInstantaneaRequestDTO dto);
    MensagemInstantaneaResponseDTO getById(Integer id);
    List<MensagemInstantaneaResponseDTO> listAll();
    Page<MensagemInstantaneaResponseDTO> list(Pageable pageable);
    List<MensagemInstantaneaResponseDTO> listByRemetente(Integer usuarioId);
    MensagemInstantaneaResponseDTO update(Integer id, MensagemInstantaneaUpdateDTO dto);
    void delete(Integer id);
}
