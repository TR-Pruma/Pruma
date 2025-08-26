package com.br.pruma.core.repository;

import com.br.pruma.core.domain.InsumoFornecedor;
import com.br.pruma.core.domain.InsumoFornecedorAux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsumoFornecedorRepository  extends JpaRepository<InsumoFornecedor, InsumoFornecedorAux> {
    List<InsumoFornecedor> findByIdInsumoId(Integer insumoId);

}

