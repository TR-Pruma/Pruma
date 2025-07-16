package com.br.pruma.infra.repository;

import com.br.pruma.core.domain.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtividadeRepository extends JpaRepository<Atividade, Integer> {
}
