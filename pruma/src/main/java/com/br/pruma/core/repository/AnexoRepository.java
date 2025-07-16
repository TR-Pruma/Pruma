package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Anexo;

import java.util.List;
import java.util.Optional;

public interface AnexoRepository {

    List<Anexo> findAll();

    Optional<Anexo> findById(Long id);

    Anexo save(Anexo anexo);

    void deleteById(Long id);
}
