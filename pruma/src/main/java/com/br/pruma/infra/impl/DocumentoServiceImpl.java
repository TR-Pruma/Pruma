package com.br.pruma.infra.impl;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final ProjetoRepository projetoRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final DocumentoMapper documentoMapper;

    @Value("${app.upload.dir:./uploads}")
    private String uploadDir;

    @Override
    @Transactional
    public DocumentoResponseDTO upload(DocumentoRequestDTO request) {
        MultipartFile arquivo = request.getArquivo();
        validarArquivo(arquivo);

        Projeto projeto = projetoRepository.findByIdAndAtivoTrue(request.getProjetoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado"));

        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByIdAndAtivoTrue(request.getTipoDocumentoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tipo de documento não encontrado"));

        String caminhoArquivo = documentoMapper.gerarCaminhoArquivo(arquivo.getOriginalFilename());
        salvarArquivo(arquivo, caminhoArquivo);

        Documento documento = documentoMapper.toEntity(request, projeto, tipoDocumento, caminhoArquivo);
        documento = documentoRepository.save(documento);

        return documentoMapper.toResponse(documento);
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentoResponseDTO buscarPorId(Integer id) {
        Documento documento = buscarDocumentoAtivo(id);
        return documentoMapper.toResponse(documento);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentoResponseDTO> listarPorProjeto(Integer projetoId, Pageable pageable) {
        if (!projetoRepository.existsByIdAndAtivoTrue(projetoId)) {
            throw new RecursoNaoEncontradoException("Projeto não encontrado");
        }

        return documentoRepository.findByProjetoIdAndAtivoTrueOrderByDataUploadDesc(projetoId, pageable)
                .map(documentoMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoResponseDTO> listarPorTipoDocumento(Integer tipoDocumentoId) {
        if (!tipoDocumentoRepository.existsByIdAndAtivoTrue(tipoDocumentoId)) {
            throw new RecursoNaoEncontradoException("Tipo de documento não encontrado");
        }

        return documentoRepository.findByTipoDocumentoIdAndAtivoTrueOrderByDataUploadDesc(tipoDocumentoId)
                .stream()
                .map(documentoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoResponseDTO> listarPorProjetoETipo(Integer projetoId, Integer tipoDocumentoId) {
        if (!projetoRepository.existsByIdAndAtivoTrue(projetoId)) {
            throw new RecursoNaoEncontradoException("Projeto não encontrado");
        }
        if (!tipoDocumentoRepository.existsByIdAndAtivoTrue(tipoDocumentoId)) {
            throw new RecursoNaoEncontradoException("Tipo de documento não encontrado");
        }

        return documentoRepository.findByProjetoIdAndTipoDocumentoIdAndAtivoTrue(projetoId, tipoDocumentoId)
                .stream()
                .map(documentoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Resource download(Integer id) {
        try {
            Documento documento = buscarDocumentoAtivo(id);
            Path arquivo = Paths.get(uploadDir).resolve(documento.getCaminhoArquivo());
            Resource resource = new UrlResource(arquivo.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Não foi possível ler o arquivo");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao acessar o arquivo", e);
        }
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        Documento documento = buscarDocumentoAtivo(id);
        documento.setAtivo(false);
        documentoRepository.save(documento);
    }

    private Documento buscarDocumentoAtivo(Integer id) {
        return documentoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Documento não encontrado"));
    }

    private void validarArquivo(MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new IllegalArgumentException("Arquivo inválido ou vazio");
        }
        // Adicione outras validações conforme necessário (tipo, tamanho, etc.)
    }

    private void salvarArquivo(MultipartFile arquivo, String nomeArquivo) {
        try {
            Path diretorio = Paths.get(uploadDir);
            if (!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
            }

            Path destino = diretorio.resolve(nomeArquivo);
            Files.copy(arquivo.getInputStream(), destino);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar o arquivo", e);
        }
    }
}
