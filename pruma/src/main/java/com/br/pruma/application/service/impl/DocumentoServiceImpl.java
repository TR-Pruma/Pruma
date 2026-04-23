package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.DocumentoRequestDTO;
import com.br.pruma.application.dto.response.DocumentoResponseDTO;
import com.br.pruma.application.mapper.DocumentoMapper;
import com.br.pruma.application.service.DocumentoService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Documento;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.domain.TipoDocumento;
import com.br.pruma.core.repository.DocumentoRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import com.br.pruma.core.repository.TipoDocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository repository;
    private final ProjetoRepository projetoRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final DocumentoMapper mapper;

    @Override
    public DocumentoResponseDTO upload(DocumentoRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Projeto com ID " + dto.getProjetoId() + " não encontrado."));
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(dto.getTipoDocumentoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Tipo de documento com ID " + dto.getTipoDocumentoId() + " não encontrado."));
        String caminhoArquivo = mapper.gerarCaminhoArquivo(
                dto.getArquivo().getOriginalFilename());
        Documento entity = mapper.toEntity(dto, projeto, tipoDocumento, caminhoArquivo);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentoResponseDTO buscarPorId(Integer id) {
        return mapper.toResponse(
                repository.findByIdAndAtivoTrue(id)
                        .orElseThrow(() -> new RecursoNaoEncontradoException(
                                "Documento com ID " + id + " não encontrado.")));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentoResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable) {
        return repository
                .findByProjetoIdAndAtivoTrueOrderByDataUploadDesc(projetoId, pageable)
                .map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoResponseDTO> listarPorTipoDocumento(Integer tipoDocumentoId) {
        return repository
                .findByTipoDocumentoIdAndAtivoTrueOrderByDataUploadDesc(tipoDocumentoId)
                .stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoResponseDTO> listarPorProjetoETipo(Integer projetoId, Integer tipoDocumentoId) {
        return repository
                .findByProjetoIdAndTipoDocumentoIdAndAtivoTrue(projetoId, tipoDocumentoId)
                .stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Resource download(Integer id) {
        Documento documento = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Documento com ID " + id + " não encontrado."));
        try {
            Path caminho = Paths.get(documento.getCaminhoArquivo());
            Resource resource = new UrlResource(caminho.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RecursoNaoEncontradoException("Arquivo físico não encontrado para o documento " + id);
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new RecursoNaoEncontradoException("Caminho inválido para o documento " + id);
        }
    }

    @Override
    public void deletar(Integer id) {
        Documento entity = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Documento com ID " + id + " não encontrado."));
        entity.setAtivo(false);
        repository.save(entity);
    }
}
