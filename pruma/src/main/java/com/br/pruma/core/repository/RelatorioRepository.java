package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Integer> {

    /**
     * Busca relatórios de uma obra específica.
     */
    List<Relatorio> findByObraId(Integer obraId);
}

