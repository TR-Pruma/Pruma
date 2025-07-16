package com.br.pruma.infra.impl;

import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.repository.AtividadeRepository;

import java.util.List;
import java.util.Optional;

public class AtividadeRepositoryImpl implements AtividadeRepository {

    private final AtividadeRepository repository;

    public AtividadeRepositoryImpl(AtividadeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Atividade> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Atividade> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Atividade save(Atividade atividade) {
        return repository.save(atividade);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
