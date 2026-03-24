package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, String> {

    /**
     * Busca empresa pelo CNPJ.
     */
    Optional<Empresa> findByCnpj(String cnpj);

    /**
     * Remove empresa pelo CNPJ.
     */
    void deleteByCnpj(String cnpj);

    /**
     * Verifica se existe empresa com determinado CNPJ.
     */
    boolean existsByCnpj(String cnpj);
}
