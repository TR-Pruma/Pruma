package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Atividade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Atividade.
 */
public interface AtividadeRepositoryPort {

    Atividade save(Atividade atividade);

    Optional<Atividade> findById(Integer id);

    List<Atividade> findAll();

    Page<Atividade> findAll(Pageable pageable);

    void deleteById(Integer id);

    void delete(Atividade atividade);

    boolean existsById(Integer id);
}
