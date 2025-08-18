package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {

    boolean existsByCnpj(String cnpj);
}
