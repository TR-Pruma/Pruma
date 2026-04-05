package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.DocumentoRequestDTO;
import com.br.pruma.application.dto.response.DocumentoResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DocumentoService {
    DocumentoResponseDTO upload(DocumentoRequestDTO dto);
    DocumentoResponseDTO buscarPorId(Integer id);
    Page<DocumentoResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable);
    List<DocumentoResponseDTO> listarPorTipoDocumento(Integer tipoDocumentoId);
    List<DocumentoResponseDTO> listarPorProjetoETipo(Integer projetoId, Integer tipoDocumentoId);
    Resource download(Integer id);
    void deletar(Integer id);
}
