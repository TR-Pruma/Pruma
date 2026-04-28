package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.StatusEquipamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de StatusEquipamento.
 */
public interface StatusEquipamentoRepositoryPort {

    StatusEquipamento save(StatusEquipamento statusEquipamento);

    Optional<StatusEquipamento> findById(Integer id);

    List<StatusEquipamento> findAll();

    Page<StatusEquipamento> findAll(Pageable pageable);

    void delete(StatusEquipamento statusEquipamento);

    void deleteById(Integer id);

    boolean existsById(Integer id);
}