package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.DocumentoRequestDTO;
import com.br.pruma.application.dto.response.DocumentoResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DocumentoService {

    /**
     * Faz o upload de um novo documento
     */
    DocumentoResponseDTO upload(DocumentoRequestDTO request);

    /**
     * Busca um documento por ID
     */
    DocumentoResponseDTO buscarPorId(Integer id);

    /**
     * Lista documentos de um projeto de forma paginada
     */
    Page<DocumentoResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable);

    /**
     * Lista documentos por tipo de documento
     */
    List<DocumentoResponseDTO> listarPorTipoDocumento(Integer tipoDocumentoId);

    /**
     * Lista documentos por projeto e tipo
     */
    List<DocumentoResponseDTO> listarPorProjetoETipo(Integer projetoId, Integer tipoDocumentoId);

    /**
     * Faz o download de um documento
     */
    Resource download(Integer id);

    /**
     * Exclui logicamente um documento
     */
    void deletar(Integer id);
}

