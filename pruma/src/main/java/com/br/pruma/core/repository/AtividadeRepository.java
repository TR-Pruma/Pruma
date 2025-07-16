package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Atividade;

import java.util.List;
import java.util.Optional;

public interface AtividadeRepository {
    List<Atividade> findAll();
    Optional<Atividade> findById(Integer id);
    Atividade save(Atividade atividade);
    void deleteById(Integer id);
}
