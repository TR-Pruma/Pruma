package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Material.
 */
public interface MaterialRepositoryPort {

    Material save(Material material);

    Optional<Material> findById(Integer id);

    List<Material> findAll();

    Page<Material> findAll(Pageable pageable);

    /** Busca material pela descrição exata. */
    Optional<Material> findByDescricao(String descricao);

    /** Lista todos os materiais ordenados alfabeticamente pela descrição. */
    List<Material> findAllByOrderByDescricaoAsc();

    void deleteById(Integer id);

    void delete(Material material);

    boolean existsById(Integer id);
}
