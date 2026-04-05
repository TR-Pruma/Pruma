package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;
import com.br.pruma.application.dto.update.EmpresaUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmpresaService {
    EmpresaResponseDTO create(EmpresaRequestDTO dto);
    EmpresaResponseDTO getById(String cnpj);
    List<EmpresaResponseDTO> listAll();
    Page<EmpresaResponseDTO> list(Pageable pageable);
    EmpresaResponseDTO update(String cnpj, EmpresaUpdateDTO dto);
    EmpresaResponseDTO replace(String cnpj, EmpresaRequestDTO dto);
    void delete(String cnpj);
}
