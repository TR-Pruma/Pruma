package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.TipoDocumento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de TipoDocumento.
 */
public interface TipoDocumentoRepositoryPort {

    TipoDocumento save(TipoDocumento tipoDocumento);

    Optional<TipoDocumento> findById(Integer id);

    List<TipoDocumento> findAll();

    Page<TipoDocumento> findAll(Pageable pageable);

    /** Busca tipos de documento cuja descrição contenha o texto informado (case-insensitive). */
    List<TipoDocumento> findByDescricaoContainingIgnoreCase(String descricao);

    void deleteById(Integer id);

    void delete(TipoDocumento tipoDocumento);

    boolean existsById(Integer id);
}
