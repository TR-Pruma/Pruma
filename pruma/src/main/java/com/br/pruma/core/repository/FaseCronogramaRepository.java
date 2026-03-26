package com.br.pruma.core.repository;

import com.br.pruma.core.domain.FaseCronograma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaseCronogramaRepository extends JpaRepository<FaseCronograma, Integer> {
}

