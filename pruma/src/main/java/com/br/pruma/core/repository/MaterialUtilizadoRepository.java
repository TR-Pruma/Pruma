package com.br.pruma.core.repository;

import com.br.pruma.core.domain.MaterialUtilizado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialUtilizadoRepository extends JpaRepository<MaterialUtilizado, Integer> {

    /**
     * Retorna todos os registros de material utilizado para uma dada atividade.
     */
    List<MaterialUtilizado> findAllByAtividade_Id(Integer atividadeId);

    /**
     * Retorna todos os registros de material utilizado para um dado material.
     */
    List<MaterialUtilizado> findAllByMaterial_Id(Integer materialId);

    /**
     * Retorna todos os registros de material utilizado de um projeto,
     * navegando pelo relacionamento Atividade → Projeto.
     */
    List<MaterialUtilizado> findAllByAtividade_Projeto_Id(Integer projetoId);
}
