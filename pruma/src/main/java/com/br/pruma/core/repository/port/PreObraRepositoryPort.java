package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.PreObra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de PreObra.
 */
public interface PreObraRepositoryPort {

    PreObra save(PreObra preObra);

    Optional<PreObra> findById(Integer id);

    List<PreObra> findAll();

    Page<PreObra> findAll(Pageable pageable);

    /** Lista todos os registros de pré-obra vinculados a uma obra. */
    List<PreObra> findAllByObra_Id(Integer obraId);

    /** Lista registros cuja data de início esteja entre as datas informadas. */
    List<PreObra> findAllByDataInicioBetween(LocalDate startDate, LocalDate endDate);

    /** Lista registros com data de início igual à informada. */
    List<PreObra> findAllByDataInicio(LocalDate date);

    void deleteById(Integer id);

    void delete(PreObra preObra);

    boolean existsById(Integer id);
}