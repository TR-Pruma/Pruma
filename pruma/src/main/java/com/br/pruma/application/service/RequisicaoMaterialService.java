package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.RequisicaoMaterialRequestDTO;
import com.br.pruma.application.dto.response.RequisicaoMaterialResponseDTO;
import com.br.pruma.application.dto.update.RequisicaoMaterialUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RequisicaoMaterialService {
    RequisicaoMaterialResponseDTO create(RequisicaoMaterialRequestDTO dto);
    RequisicaoMaterialResponseDTO getById(Integer id);
    List<RequisicaoMaterialResponseDTO> listAll();
    Page<RequisicaoMaterialResponseDTO> list(Pageable pageable);
    List<RequisicaoMaterialResponseDTO> listByProjeto(Integer projetoId);
    RequisicaoMaterialResponseDTO update(Integer id, RequisicaoMaterialUpdateDTO dto);
    RequisicaoMaterialResponseDTO replace(Integer id, RequisicaoMaterialRequestDTO dto);
    void delete(Integer id);
}
