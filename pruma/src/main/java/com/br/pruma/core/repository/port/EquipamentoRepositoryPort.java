package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.Equipamento;
import com.br.pruma.core.enums.StatusEquipamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de Equipamento.
 */
public interface EquipamentoRepositoryPort {

    Equipamento save(Equipamento equipamento);

    Optional<Equipamento> findById(Integer id);

    /** Busca por ID incluindo registros inativos. */
    Optional<Equipamento> findByIdIncludingInativos(Integer id);

    List<Equipamento> findAll();

    Page<Equipamento> findAll(Pageable pageable);

    /** Pesquisa equipamentos com filtros opcionais, incluindo inativos. */
    Page<Equipamento> searchIncludingInativos(String nome, StatusEquipamento status, Boolean ativo, Pageable pageable);

    /** Soft delete em lote. */
    void softDeleteManyByIds(List<Integer> ids);

    /** Reativar em lote. */
    void reativarManyByIds(List<Integer> ids);

    void deleteById(Integer id);

    void delete(Equipamento equipamento);

    boolean existsById(Integer id);
}
