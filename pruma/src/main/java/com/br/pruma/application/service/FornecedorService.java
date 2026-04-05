package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.FornecedorRequestDTO;
import com.br.pruma.application.dto.response.FornecedorResponseDTO;
import com.br.pruma.application.dto.update.FornecedorUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FornecedorService {
    FornecedorResponseDTO create(FornecedorRequestDTO dto);
    FornecedorResponseDTO getById(Integer id);
    List<FornecedorResponseDTO> listAll();
    Page<FornecedorResponseDTO> list(Pageable pageable);
    FornecedorResponseDTO update(Integer id, FornecedorUpdateDTO dto);
    FornecedorResponseDTO replace(Integer id, FornecedorRequestDTO dto);
    void delete(Integer id);
}
