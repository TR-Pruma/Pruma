package com.br.pruma.infra.impl;

import com.br.pruma.core.domain.Auditoria;
import com.br.pruma.core.repository.AuditoriaRepository;

import java.util.List;
import java.util.Optional;

public class AuditoriaRepositoryImpl implements AuditoriaRepository {

    @Override
    public List<Auditoria> findAll() {
        return List.of();
    }


    @Override
    public Optional<Auditoria> findById(Integer id) {
        return Optional.empty();
    }


    @Override
    public Auditoria save(Auditoria auditoria) {
        return null;
    }


    @Override
    public void deleteById(Integer id) {

    }
}
