package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Obra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Obra.
 */
public interface ObraRepositoryPort {

    Obra save(Obra obra);

    Optional<Obra> findById(Integer id);

    List<Obra> findAll();

    Page<Obra> findAll(Pageable pageable);

    /** Lista todas as obras de um projeto. */
    List<Obra> findAllByProjetoId(Integer projetoId);

    /** Lista obras cuja data de início esteja entre as datas informadas. */
    List<Obra> findAllByDataInicioBetween(LocalDate start, LocalDate end);

    /** Lista obras pela descrição (LIKE, case-insensitive). */
    List<Obra> findAllByDescricaoContainingIgnoreCase(String descricao);

    /** Verifica existência de obras para um projeto. */
    boolean existsByProjetoId(Integer projetoId);

    void deleteById(Integer id);

    void delete(Obra obra);

    boolean existsById(Integer id);
}
