package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Integer> {
}

