package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {
}