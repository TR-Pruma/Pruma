package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.MensagemInstantaneaAuxRequestDTO;
import com.br.pruma.application.dto.response.MensagemInstantaneaAuxResponseDTO;
import com.br.pruma.application.dto.update.MensagemInstantaneaAuxUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MensagemInstantaneaAuxService {
    MensagemInstantaneaAuxResponseDTO create(MensagemInstantaneaAuxRequestDTO dto);
    MensagemInstantaneaAuxResponseDTO getById(Integer id);
    List<MensagemInstantaneaAuxResponseDTO> listAll();
    Page<MensagemInstantaneaAuxResponseDTO> list(Pageable pageable);
    MensagemInstantaneaAuxResponseDTO update(Integer id, MensagemInstantaneaAuxUpdateDTO dto);
    void delete(Integer id);
}
