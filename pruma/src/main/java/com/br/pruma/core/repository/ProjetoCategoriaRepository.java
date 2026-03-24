package com.br.pruma.core.repository;

import com.br.pruma.core.domain.ProjetoCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetoCategoriaRepository extends JpaRepository<ProjetoCategoria, Integer> {
}

