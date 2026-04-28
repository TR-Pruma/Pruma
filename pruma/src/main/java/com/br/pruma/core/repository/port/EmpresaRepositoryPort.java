package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Empresa.
 * A PK de Empresa é o CNPJ (String).
 */
public interface EmpresaRepositoryPort {

    Empresa save(Empresa empresa);

    Optional<Empresa> findById(String cnpj);

    /** Busca empresa pelo CNPJ. */
    Optional<Empresa> findByCnpj(String cnpj);

    List<Empresa> findAll();

    Page<Empresa> findAll(Pageable pageable);

    /** Remove empresa pelo CNPJ. */
    void deleteByCnpj(String cnpj);

    /** Verifica se existe empresa com o CNPJ informado. */
    boolean existsByCnpj(String cnpj);

    void deleteById(String cnpj);

    void delete(Empresa empresa);

    boolean existsById(String cnpj);
}
