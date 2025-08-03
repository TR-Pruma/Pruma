package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Documento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Integer> {

    Optional<Documento> findByIdAndAtivoTrue(Integer id);

    Page<Documento> findByProjetoIdAndAtivoTrueOrderByDataUploadDesc(Integer projetoId, Pageable pageable);

    List<Documento> findByTipoDocumentoIdAndAtivoTrueOrderByDataUploadDesc(Integer tipoDocumentoId);

    List<Documento> findByProjetoIdAndTipoDocumentoIdAndAtivoTrue(Integer projetoId, Integer tipoDocumentoId);

    boolean existsByNomeArquivoAndProjetoIdAndAtivoTrue(String nomeArquivo, Integer projetoId);

    long countByProjetoIdAndAtivoTrue(Integer projetoId);
}
