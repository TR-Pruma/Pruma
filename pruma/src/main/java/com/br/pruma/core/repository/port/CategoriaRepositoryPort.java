package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Categoria.
 */
public interface CategoriaRepositoryPort {

    Categoria save(Categoria categoria);

    Optional<Categoria> findById(Integer id);

    List<Categoria> findAll();

    Page<Categoria> findAll(Pageable pageable);

    /** Busca categorias cujo nome contenha o texto informado (case-insensitive). */
    List<Categoria> findByNomeContainingIgnoreCase(String nome);

    void deleteById(Integer id);

    void delete(Categoria categoria);

    boolean existsById(Integer id);
}
