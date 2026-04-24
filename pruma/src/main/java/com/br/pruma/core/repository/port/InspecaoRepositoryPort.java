package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Inspecao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Inspecao.
 */
public interface InspecaoRepositoryPort {

    Inspecao save(Inspecao inspecao);

    Optional<Inspecao> findById(Integer id);

    List<Inspecao> findAll();

    Page<Inspecao> findAll(Pageable pageable);

    /** Busca todas as inspeções de um projeto. */
    List<Inspecao> findAllByProjetoId(Integer projetoId);

    void deleteById(Integer id);

    void delete(Inspecao inspecao);

    boolean existsById(Integer id);
}
