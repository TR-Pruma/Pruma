package com.br.pruma.core.repository;


import com.br.pruma.core.domain.RequisicaoMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequisicaoMaterialRepository extends JpaRepository<RequisicaoMaterial, Integer> {

    /**
     * Busca requisições de uma obra específica.
     */
    List<RequisicaoMaterial> findByObraId(Integer obraId);

    /**
     * Busca requisições de um material específico.
     */
    List<RequisicaoMaterial> findByMaterialId(Integer materialId);
}
