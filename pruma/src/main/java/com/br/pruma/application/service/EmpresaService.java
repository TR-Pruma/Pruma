package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;
import com.br.pruma.application.dto.update.EmpresaUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmpresaService {
    EmpresaResponseDTO create(EmpresaRequestDTO dto);
    EmpresaResponseDTO getById(Integer id);
    List<EmpresaResponseDTO> listAll();
    Page<EmpresaResponseDTO> list(Pageable pageable);
    EmpresaResponseDTO update(Integer id, EmpresaUpdateDTO dto);
    EmpresaResponseDTO replace(Integer id, EmpresaRequestDTO dto);
    void delete(Integer id);
}
