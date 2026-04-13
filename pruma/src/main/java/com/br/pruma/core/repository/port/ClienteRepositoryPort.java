package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Cliente.
 */
public interface ClienteRepositoryPort {

    Cliente save(Cliente cliente);

    Optional<Cliente> findById(Integer id);

    /** Busca cliente pelo CPF. */
    Optional<Cliente> findByCpf(String cpf);

    List<Cliente> findAll();

    Page<Cliente> findAll(Pageable pageable);

    void deleteById(Integer id);

    void delete(Cliente cliente);

    boolean existsById(Integer id);
}
