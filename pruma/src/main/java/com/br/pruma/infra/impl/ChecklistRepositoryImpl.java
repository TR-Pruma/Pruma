package com.br.pruma.infra.impl;

import com.br.pruma.core.domain.Checklist;
import com.br.pruma.core.repository.ChecklistRepository;

import java.util.List;
import java.util.Optional;

public class ChecklistRepositoryImpl implements ChecklistRepository {

    private final ChecklistRepository repository;

    public ChecklistRepositoryImpl(ChecklistRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Checklist> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Checklist> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Checklist save(Checklist checklist) {
        return repository.save(checklist);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
