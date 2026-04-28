package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Documento;
import com.br.pruma.core.repository.DocumentoRepository;
import com.br.pruma.core.repository.port.DocumentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link DocumentoRepositoryPort}
 * delegando ao {@link DocumentoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class DocumentoRepositoryAdapter implements DocumentoRepositoryPort {

    private final DocumentoRepository documentoRepository;

    @Override
    public Documento save(Documento documento) {
        return documentoRepository.save(documento);
    }

    @Override
    public Optional<Documento> findById(Integer id) {
        return documentoRepository.findById(id);
    }

    @Override
    public Optional<Documento> findByIdAndAtivoTrue(Integer id) {
        return documentoRepository.findByIdAndAtivoTrue(id);
    }

    @Override
    public List<Documento> findAll() {
        return documentoRepository.findAll();
    }

    @Override
    public Page<Documento> findAll(Pageable pageable) {
        return documentoRepository.findAll(pageable);
    }

    @Override
    public Page<Documento> findByProjetoIdAndAtivoTrueOrderByDataUploadDesc(Integer projetoId, Pageable pageable) {
        return documentoRepository.findByProjetoIdAndAtivoTrueOrderByDataUploadDesc(projetoId, pageable);
    }

    @Override
    public List<Documento> findByTipoDocumentoIdAndAtivoTrueOrderByDataUploadDesc(Integer tipoDocumentoId) {
        return documentoRepository.findByTipoDocumentoIdAndAtivoTrueOrderByDataUploadDesc(tipoDocumentoId);
    }

    @Override
    public List<Documento> findByProjetoIdAndTipoDocumentoIdAndAtivoTrue(Integer projetoId, Integer tipoDocumentoId) {
        return documentoRepository.findByProjetoIdAndTipoDocumentoIdAndAtivoTrue(projetoId, tipoDocumentoId);
    }

    @Override
    public boolean existsByNomeArquivoAndProjetoIdAndAtivoTrue(String nomeArquivo, Integer projetoId) {
        return documentoRepository.existsByNomeArquivoAndProjetoIdAndAtivoTrue(nomeArquivo, projetoId);
    }

    @Override
    public long countByProjetoIdAndAtivoTrue(Integer projetoId) {
        return documentoRepository.countByProjetoIdAndAtivoTrue(projetoId);
    }

    @Override
    public void deleteById(Integer id) {
        documentoRepository.deleteById(id);
    }

    @Override
    public void delete(Documento documento) {
        documentoRepository.delete(documento);
    }

    @Override
    public boolean existsById(Integer id) {
        return documentoRepository.existsById(id);
    }
}
