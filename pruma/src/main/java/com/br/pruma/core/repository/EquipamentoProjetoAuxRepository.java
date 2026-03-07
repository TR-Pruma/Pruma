package com.br.pruma.core.repository;

import com.br.pruma.core.domain.EquipamentoProjetoAux;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipamentoProjetoAuxRepository extends JpaRepository<EquipamentoProjetoAux, Long> {
}
