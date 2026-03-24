package com.br.pruma.core.repository;

import com.br.pruma.core.domain.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {
    // TipoDocumento é tabela de catálogo — sem soft-delete, usa findById padrão
    List<TipoDocumento> findByDescricaoContainingIgnoreCase(String descricao);
}
