package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Cronograma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CronogramaRepository extends JpaRepository<Cronograma, Integer> {


    // Busca todos os cronogramas de um projeto
    List<Cronograma> findAllByProjetoId(Integer projetoId);

    // Busca um cronograma pelo id **e** projeto
    Optional<Cronograma> findByIdAndProjetoId(Integer id, Integer projetoId);

}