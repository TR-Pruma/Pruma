package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.InsumoFornecedor;
import com.br.pruma.core.domain.InsumoFornecedorAuxId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de InsumoFornecedor.
 * A PK é composta (InsumoFornecedorAuxId).
 */
public interface InsumoFornecedorRepositoryPort {

    InsumoFornecedor save(InsumoFornecedor insumoFornecedor);

    Optional<InsumoFornecedor> findById(InsumoFornecedorAuxId id);

    List<InsumoFornecedor> findAll();

    Page<InsumoFornecedor> findAll(Pageable pageable);

    /** Busca todos os fornecedores de um insumo. */
    List<InsumoFornecedor> findByIdInsumoId(Integer insumoId);

    void deleteById(InsumoFornecedorAuxId id);

    void delete(InsumoFornecedor insumoFornecedor);

    boolean existsById(InsumoFornecedorAuxId id);
}
