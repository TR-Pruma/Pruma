package com.br.pruma.infra.impl;

import com.br.pruma.core.domain.Anexo;
import com.br.pruma.core.repository.AnexoRepository;

import java.util.List;
import java.util.Optional;

public class AnexoRepositoryImpl implements AnexoRepository {

    @Override
    public List<Anexo> findAll() {
        return List.of();
    }


    @Override
    public Optional<Anexo> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Anexo save(Anexo anexo) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
