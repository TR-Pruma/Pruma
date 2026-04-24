package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Insumo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Insumo.
 */
public interface InsumoRepositoryPort {

    Insumo save(Insumo insumo);

    Optional<Insumo> findById(Integer id);

    List<Insumo> findAll();

    Page<Insumo> findAll(Pageable pageable);

    void deleteById(Integer id);

    void delete(Insumo insumo);

    boolean existsById(Integer id);
}
