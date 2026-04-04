package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.DocumentoRequestDTO;
import com.br.pruma.application.dto.response.DocumentoResponseDTO;
import com.br.pruma.application.dto.update.DocumentoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DocumentoService {
    DocumentoResponseDTO create(DocumentoRequestDTO dto);
    DocumentoResponseDTO getById(Integer id);
    List<DocumentoResponseDTO> listAll();
    Page<DocumentoResponseDTO> list(Pageable pageable);
    DocumentoResponseDTO update(Integer id, DocumentoUpdateDTO dto);
    void delete(Integer id);
}
