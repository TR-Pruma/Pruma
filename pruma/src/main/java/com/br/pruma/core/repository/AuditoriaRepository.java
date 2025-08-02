package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Auditoria;

import java.util.List;
import java.util.Optional;

public interface AuditoriaRepository {
    List<Auditoria> findAll();

    Optional<Auditoria> findById(Integer id);

    Auditoria save(Auditoria auditoria);

    void deleteById(Integer id);
}
