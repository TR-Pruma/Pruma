package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Cronograma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Cronograma.
 */
public interface CronogramaRepositoryPort {

    Cronograma save(Cronograma cronograma);

    Optional<Cronograma> findById(Integer id);

    List<Cronograma> findAll();

    Page<Cronograma> findAll(Pageable pageable);

    /** Busca todos os cronogramas de um projeto. */
    List<Cronograma> findAllByProjetoId(Integer projetoId);

    /** Busca cronograma por id e projeto. */
    Optional<Cronograma> findByIdAndProjetoId(Integer id, Integer projetoId);

    void deleteById(Integer id);

    void delete(Cronograma cronograma);

    boolean existsById(Integer id);
}
