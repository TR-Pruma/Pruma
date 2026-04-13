package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Auditoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Port de saída (Output Port) para persistência de Auditoria.
 */
public interface AuditoriaRepositoryPort {

    Auditoria save(Auditoria auditoria);

    Optional<Auditoria> findById(UUID id);

    List<Auditoria> findAll();

    Page<Auditoria> findAll(Pageable pageable);

    void deleteById(UUID id);

    void delete(Auditoria auditoria);

    boolean existsById(UUID id);
}
