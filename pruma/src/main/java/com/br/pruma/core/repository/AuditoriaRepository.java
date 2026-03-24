package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Auditoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuditoriaRepository extends JpaRepository<Auditoria, UUID> {
}
