package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.EquipamentoProjeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de EquipamentoProjeto.
 * A PK é Long.
 */
public interface EquipamentoProjetoRepositoryPort {

    EquipamentoProjeto save(EquipamentoProjeto equipamentoProjeto);

    Optional<EquipamentoProjeto> findById(Long id);

    List<EquipamentoProjeto> findAll();

    Page<EquipamentoProjeto> findAll(Pageable pageable);

    /** Verifica se já existe alocação do equipamento na data informada. */
    boolean existsByEquipamentoIdAndDataAlocacao(Long equipamentoId, LocalDate dataAlocacao);

    void deleteById(Long id);

    void delete(EquipamentoProjeto equipamentoProjeto);

    boolean existsById(Long id);
}
