package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Checklist;

import java.util.List;
import java.util.Optional;

public interface ChecklistRepository {
    List<Checklist> findAll();
    Optional<Checklist> findById(Integer id);
    Checklist save(Checklist checklist);
    void deleteById(Integer id);
}
