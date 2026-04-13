package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Anexo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Anexo.
 */
public interface AnexoRepositoryPort {

    Anexo save(Anexo anexo);

    Optional<Anexo> findById(Integer id);

    List<Anexo> findAll();

    Page<Anexo> findAll(Pageable pageable);

    void deleteById(Integer id);

    void delete(Anexo anexo);

    boolean existsById(Integer id);
}
