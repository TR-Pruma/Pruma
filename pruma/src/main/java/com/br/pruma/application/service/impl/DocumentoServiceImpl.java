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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentoServiceImpl implements DocumentoService {

    private static final String UPLOAD_DIR = "uploads/documentos";

    private final DocumentoRepository repository;
    private final ProjetoRepository projetoRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final DocumentoMapper mapper;

    @Override
    public DocumentoResponseDTO upload(DocumentoRequestDTO request) {
        Projeto projeto = projetoRepository.findById(request.getProjetoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Projeto com ID " + request.getProjetoId() + " não encontrado."));

        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(request.getTipoDocumentoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "TipoDocumento com ID " + request.getTipoDocumentoId() + " não encontrado."));

        MultipartFile arquivo = request.getArquivo();
        String nomeOriginal = arquivo.getOriginalFilename() != null
                ? arquivo.getOriginalFilename() : "arquivo_sem_nome";
        String caminhoRelativo = mapper.gerarCaminhoArquivo(nomeOriginal);

        try {
            Path destino = Paths.get(UPLOAD_DIR).resolve(caminhoRelativo);
            Files.createDirectories(destino.getParent());
            Files.copy(arquivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar arquivo: " + e.getMessage(), e);
        }

        Documento documento = mapper.toEntity(request, projeto, tipoDocumento, caminhoRelativo);
        return mapper.toResponse(repository.save(documento));
    }

    @Override
    public DocumentoResponseDTO buscarPorId(Integer id) {
        return repository.findByIdAndAtivoTrue(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Documento com ID " + id + " não encontrado."));
    }

    @Override
    public Page<DocumentoResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable) {
        return repository.findByProjetoIdAndAtivoTrueOrderByDataUploadDesc(projetoId, pageable)
                .map(mapper::toResponse);
    }

    @Override
    public List<DocumentoResponseDTO> listarPorTipoDocumento(Integer tipoDocumentoId) {
        return repository.findByTipoDocumentoIdAndAtivoTrueOrderByDataUploadDesc(tipoDocumentoId)
                .stream().map(mapper::toResponse).toList();
    }

    @Override
    public List<DocumentoResponseDTO> listarPorProjetoETipo(Integer projetoId, Integer tipoDocumentoId) {
        return repository.findByProjetoIdAndTipoDocumentoIdAndAtivoTrue(projetoId, tipoDocumentoId)
                .stream().map(mapper::toResponse).toList();
    }

    @Override
    public Resource download(Integer id) {
        Documento documento = repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Documento com ID " + id + " não encontrado."));
        try {
            Path caminho = Paths.get(UPLOAD_DIR).resolve(documento.getCaminhoArquivo()).normalize();
            Resource resource = new UrlResource(caminho.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RecursoNaoEncontradoException("Arquivo físico não encontrado: " + documento.getNomeArquivo());
            }
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Caminho de arquivo inválido.", e);
        }
    }

    @Override
    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Não é possível deletar. Documento com ID " + id + " não existe.");
        }
        repository.deleteById(id);
    }
}
