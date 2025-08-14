package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Cronograma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronogramaRepository extends JpaRepository<Cronograma, Integer> {

}