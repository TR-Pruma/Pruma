package com.br.pruma.core.repository;

import com.br.pruma.core.domain.TecnicoDeObras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TecnicoDeObrasRepository extends JpaRepository<TecnicoDeObras, Integer> {
}
