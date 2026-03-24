package com.br.pruma.core.repository;

import com.br.pruma.core.domain.TermoGarantia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermoGarantiaRepository extends JpaRepository<TermoGarantia, Integer> {
}
