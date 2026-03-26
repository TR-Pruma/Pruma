package com.br.pruma.core.repository;


import com.br.pruma.core.domain.SubContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubContratoRepository extends JpaRepository<SubContrato, Integer> {

    /**
     * Busca subcontratos de um cliente específico.
     */
    List<SubContrato> findByClienteCpf(String clienteCpf);

    /**
     * Busca subcontratos de um projeto específico.
     */
    List<SubContrato> findByProjetoId(Integer projetoId);

    /**
     * Busca subcontratos de um cliente em um projeto específico.
     */
    List<SubContrato> findByClienteCpfAndProjetoId(String clienteCpf, Integer projetoId);
}