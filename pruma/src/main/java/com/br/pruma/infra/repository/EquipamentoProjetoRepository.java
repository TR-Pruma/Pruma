package com.br.pruma.infra.repository;

import com.br.pruma.core.domain.EquipamentoProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EquipamentoProjetoRepository extends JpaRepository<EquipamentoProjeto, Long> {
    boolean existsByEquipamentoIdAndDataAlocacao(Long equipamentoId, LocalDate dataAlocacao);


}
