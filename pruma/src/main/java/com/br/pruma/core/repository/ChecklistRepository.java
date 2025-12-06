package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {
}
