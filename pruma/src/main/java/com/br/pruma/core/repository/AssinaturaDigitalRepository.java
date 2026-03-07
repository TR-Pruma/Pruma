package com.br.pruma.core.repository;

import com.br.pruma.core.domain.AssinaturaDigital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssinaturaDigitalRepository extends JpaRepository<AssinaturaDigital, Integer> {
}
