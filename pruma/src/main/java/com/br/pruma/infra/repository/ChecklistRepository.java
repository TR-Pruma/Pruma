package com.br.pruma.infra.repository;

import com.br.pruma.core.domain.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {
}
