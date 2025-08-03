package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Cronograma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CronogramaRepository extends JpaRepository<Cronograma, Integer> {
    Optional<Cronograma> findByIdAndAtivoTrue(Integer id);

    Page<Cronograma> findByProjetoIdAndAtivoTrueOrderByDataInicioDesc(Integer projetoId, Pageable pageable);

    List<Cronograma> findByDataInicioGreaterThanEqualAndDataFimLessThanEqualAndAtivoTrue(
            LocalDate inicio, LocalDate fim);

    List<Cronograma> findByProjetoIdAndAtivoTrueOrderByDataInicioAsc(Integer projetoId);

    boolean existsByProjetoIdAndDataInicioAndDataFimAndAtivoTrue(
            Integer projetoId, LocalDate dataInicio, LocalDate dataFim);
}
