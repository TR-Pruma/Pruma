package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ApadrinhamentoRedeRequestDTO;
import com.br.pruma.application.dto.response.ApadrinhamentoRedeResponseDTO;
import com.br.pruma.application.dto.update.ApadrinhamentoRedeUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ApadrinhamentoRedeService {

    ApadrinhamentoRedeResponseDTO create(ApadrinhamentoRedeRequestDTO dto);

    ApadrinhamentoRedeResponseDTO getById(Long id);

    List<ApadrinhamentoRedeResponseDTO> listAll();

    Page<ApadrinhamentoRedeResponseDTO> list(Pageable pageable);

    /** Lista todos os apadrinhamentos em que o profissional é padrinho. */
    List<ApadrinhamentoRedeResponseDTO> listByPadrinho(Long padrinhoId);

    /** Lista todos os apadrinhamentos em que o profissional é afilhado. */
    List<ApadrinhamentoRedeResponseDTO> listByAfilhado(Long afilhadoId);

    ApadrinhamentoRedeResponseDTO update(Long id, ApadrinhamentoRedeUpdateDTO dto);

    void delete(Long id);
}