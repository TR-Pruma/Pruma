package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.MaterialUtilizado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de MaterialUtilizado.
 * Define o contrato que a camada de aplicação usa sem depender
 * diretamente de JPA ou de qualquer tecnologia de persistência.
 */
public interface MaterialUtilizadoRepositoryPort {

    /** Persiste ou atualiza um material utilizado. */
    MaterialUtilizado save(MaterialUtilizado materialUtilizado);

    /** Busca um material utilizado pelo seu ID. */
    Optional<MaterialUtilizado> findById(Integer id);

    /** Retorna todos os materiais utilizados. */
    List<MaterialUtilizado> findAll();

    /** Retorna todos os materiais utilizados paginados. */
    Page<MaterialUtilizado> findAll(Pageable pageable);

    /** Retorna todos os materiais utilizados de um projeto. */
    List<MaterialUtilizado> findByProjetoId(Integer projetoId);

    /** Remove um material utilizado pelo seu ID. */
    void deleteById(Integer id);

    /** Verifica se um material utilizado existe pelo seu ID. */
    boolean existsById(Integer id);
}
