package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.TipoDocumentoRequestDTO;
import com.br.pruma.application.dto.response.TipoDocumentoResponseDTO;
import com.br.pruma.application.dto.update.TipoDocumentoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TipoDocumentoService {
    TipoDocumentoResponseDTO create(TipoDocumentoRequestDTO dto);
    TipoDocumentoResponseDTO getById(Integer id);
    List<TipoDocumentoResponseDTO> listAll();
    Page<TipoDocumentoResponseDTO> list(Pageable pageable);
    TipoDocumentoResponseDTO update(Integer id, TipoDocumentoUpdateDTO dto);
    TipoDocumentoResponseDTO replace(Integer id, TipoDocumentoRequestDTO dto);
    void delete(Integer id);
}
