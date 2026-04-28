package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.FaseCronograma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de FaseCronograma.
 */
public interface FaseCronogramaRepositoryPort {

    FaseCronograma save(FaseCronograma faseCronograma);

    Optional<FaseCronograma> findById(Integer id);

    List<FaseCronograma> findAll();

    Page<FaseCronograma> findAll(Pageable pageable);

    List<FaseCronograma> findAllByCronogramaId(Integer cronogramaId);

    void deleteById(Integer id);

    void delete(FaseCronograma faseCronograma);

    boolean existsById(Integer id);
}
