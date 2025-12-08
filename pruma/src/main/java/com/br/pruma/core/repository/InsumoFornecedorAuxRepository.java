package com.br.pruma.core.repository;

import com.br.pruma.core.domain.InsumoFornecedorAux;
import com.br.pruma.core.domain.InsumoFornecedorAuxId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsumoFornecedorAuxRepository extends JpaRepository<InsumoFornecedorAux, InsumoFornecedorAuxId> {

    // Exemplo de query derivada: buscar por insumoId
    java.util.List<InsumoFornecedorAux> findByIdInsumoId(Integer insumoId);

    // Exemplo de query derivada: buscar por fornecedorId
    java.util.List<InsumoFornecedorAux> findByIdFornecedorId(Integer fornecedorId);

    // Exemplo de query derivada: buscar por insumo e fornecedor juntos
    java.util.Optional<InsumoFornecedorAux> findByIdInsumoIdAndIdFornecedorId(Integer insumoId, Integer fornecedorId);
}

