package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Logradouro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Logradouro.
 * Define o contrato que a camada de aplicação usa sem depender
 * diretamente de JPA ou de qualquer tecnologia de persistência.
 */
public interface LogradouroRepositoryPort {

    /** Persiste ou atualiza um logradouro. */
    Logradouro save(Logradouro logradouro);

    /** Busca um logradouro pelo seu ID. */
    Optional<Logradouro> findById(Integer id);

    /** Retorna todos os logradouros. */
    List<Logradouro> findAll();

    /** Retorna todos os logradouros paginados. */
    Page<Logradouro> findAll(Pageable pageable);

    /** Remove um logradouro pelo seu ID. */
    void deleteById(Integer id);

    /** Verifica se um logradouro existe pelo seu ID. */
    boolean existsById(Integer id);
}
