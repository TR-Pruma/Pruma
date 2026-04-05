package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.InsumoFornecedorRequestDTO;
import com.br.pruma.application.dto.response.InsumoFornecedorResponseDTO;
import com.br.pruma.application.dto.update.InsumoFornecedorUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InsumoFornecedorService {
    InsumoFornecedorResponseDTO create(InsumoFornecedorRequestDTO dto);
    InsumoFornecedorResponseDTO getById(Integer id);
    List<InsumoFornecedorResponseDTO> listAll();
    Page<InsumoFornecedorResponseDTO> list(Pageable pageable);
    List<InsumoFornecedorResponseDTO> listByInsumo(Integer insumoId);
    List<InsumoFornecedorResponseDTO> listByFornecedor(Integer fornecedorId);
    InsumoFornecedorResponseDTO update(Integer id, InsumoFornecedorUpdateDTO dto);
    void delete(Integer id);
}
