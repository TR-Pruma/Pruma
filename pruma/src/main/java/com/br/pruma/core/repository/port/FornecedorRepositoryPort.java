package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Fornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Fornecedor.
 */
public interface FornecedorRepositoryPort {

    Fornecedor save(Fornecedor fornecedor);

    Optional<Fornecedor> findById(Integer id);

    List<Fornecedor> findAll();

    Page<Fornecedor> findAll(Pageable pageable);

    /** Verifica se já existe fornecedor com o CNPJ informado. */
    boolean existsByCnpj(String cnpj);

    void deleteById(Integer id);

    void delete(Fornecedor fornecedor);

    boolean existsById(Integer id);
}
