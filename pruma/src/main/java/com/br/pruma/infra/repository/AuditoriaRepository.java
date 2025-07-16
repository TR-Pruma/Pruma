package com.br.pruma.infra.repository;

import com.br.pruma.core.domain.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRepository extends JpaRepository<Auditoria, Integer> {
}