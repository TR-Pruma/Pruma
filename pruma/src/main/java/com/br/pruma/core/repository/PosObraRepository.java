package com.br.pruma.core.repository;

import com.br.pruma.core.domain.PosObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PosObraRepository extends JpaRepository<PosObra, Long> {

    /**
     * Retorna todos os registros de pós-obra relacionados a uma obra específica.
     */
    List<PosObra> findAllByObra_Id(Long obraId);

    /**
     * Retorna todos os registros de pós-obra cuja data de conclusão esteja entre as datas informadas.
     */
    List<PosObra> findAllByDataConclusaoBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Retorna todos os registros de pós-obra com data de conclusão igual à informada.
     */
    List<PosObra> findAllByDataConclusao(LocalDate date);
}
