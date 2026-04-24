package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Logradouro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Logradouro.
 */
public interface LogradouroRepositoryPort {

    Logradouro save(Logradouro logradouro);

    Optional<Logradouro> findById(Integer id);

    List<Logradouro> findAll();

    Page<Logradouro> findAll(Pageable pageable);

    Optional<Logradouro> findByTipo(String tipo);

    List<Logradouro> findAllByOrderByTipoAsc();

    void deleteById(Integer id);

    void delete(Logradouro logradouro);

    boolean existsById(Integer id);
}
