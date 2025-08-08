package com.br.pruma.infra.repository;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;

import java.util.List;

public interface EmpresaService {
    EmpresaResponseDTO salvar(EmpresaRequestDTO dto);
    EmpresaResponseDTO buscarPorCnpj(String cnpj);
    List<EmpresaResponseDTO> listarTodas();
    void deletar(String cnpj);
}
