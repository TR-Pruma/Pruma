package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Documento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Documento.
 */
public interface DocumentoRepositoryPort {

    Documento save(Documento documento);

    Optional<Documento> findById(Integer id);

    /** Busca documento ativo pelo id. */
    Optional<Documento> findByIdAndAtivoTrue(Integer id);

    List<Documento> findAll();

    Page<Documento> findAll(Pageable pageable);

    /** Lista documentos ativos de um projeto, ordenados por data de upload desc. */
    Page<Documento> findByProjetoIdAndAtivoTrueOrderByDataUploadDesc(Integer projetoId, Pageable pageable);

    /** Lista documentos ativos de um tipo de documento, ordenados por data de upload desc. */
    List<Documento> findByTipoDocumentoIdAndAtivoTrueOrderByDataUploadDesc(Integer tipoDocumentoId);

    /** Lista documentos ativos por projeto e tipo de documento. */
    List<Documento> findByProjetoIdAndTipoDocumentoIdAndAtivoTrue(Integer projetoId, Integer tipoDocumentoId);

    /** Verifica se já existe documento ativo com o mesmo nome no projeto. */
    boolean existsByNomeArquivoAndProjetoIdAndAtivoTrue(String nomeArquivo, Integer projetoId);

    /** Conta documentos ativos de um projeto. */
    long countByProjetoIdAndAtivoTrue(Integer projetoId);

    void deleteById(Integer id);

    void delete(Documento documento);

    boolean existsById(Integer id);
}
