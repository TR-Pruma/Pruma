package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ItemOrcamentoRequestDTO;
import com.br.pruma.application.dto.response.ItemOrcamentoResponseDTO;
import com.br.pruma.application.dto.update.ItemOrcamentoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemOrcamentoService {
    ItemOrcamentoResponseDTO create(ItemOrcamentoRequestDTO dto);
    ItemOrcamentoResponseDTO getById(Integer id);
    List<ItemOrcamentoResponseDTO> listAll();
    Page<ItemOrcamentoResponseDTO> list(Pageable pageable);
    List<ItemOrcamentoResponseDTO> listByOrcamento(Integer orcamentoId);
    ItemOrcamentoResponseDTO update(Integer id, ItemOrcamentoUpdateDTO dto);
    ItemOrcamentoResponseDTO replace(Integer id, ItemOrcamentoRequestDTO dto);
    void delete(Integer id);
}
