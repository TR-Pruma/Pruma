package com.br.pruma.infra.repository;
import com.br.pruma.core.domain.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {
    Optional<Object> findByIdAndAtivoTrue(Integer tipoDocumentoId);

    boolean existsByIdAndAtivoTrue(Integer tipoDocumentoId);
    // Exemplo de query derivada:
    // List<TipoDocumento> findByDescricaoContainingIgnoreCase(String descricao);
}

