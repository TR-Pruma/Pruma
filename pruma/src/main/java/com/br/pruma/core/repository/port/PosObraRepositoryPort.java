package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.PosObra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de PosObra.
 */
public interface PosObraRepositoryPort {

    PosObra save(PosObra posObra);

    Optional<PosObra> findById(Long id);

    List<PosObra> findAll();

    Page<PosObra> findAll(Pageable pageable);

    /** Lista todos os registros de pós-obra vinculados a uma obra. */
    List<PosObra> findAllByObra_Id(Long obraId);

    /** Lista registros cuja data de conclusão esteja entre as datas informadas. */
    List<PosObra> findAllByDataConclusaoBetween(LocalDate startDate, LocalDate endDate);

    /** Lista registros com data de conclusão igual à informada. */
    List<PosObra> findAllByDataConclusao(LocalDate date);

    void deleteById(Long id);

    void delete(PosObra posObra);

    boolean existsById(Long id);
}
