package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Endereco.
 */
public interface EnderecoRepositoryPort {

    Endereco save(Endereco endereco);

    Optional<Endereco> findById(Integer id);

    List<Endereco> findAll();

    Page<Endereco> findAll(Pageable pageable);

    void deleteById(Integer id);

    void delete(Endereco endereco);

    boolean existsById(Integer id);
}
