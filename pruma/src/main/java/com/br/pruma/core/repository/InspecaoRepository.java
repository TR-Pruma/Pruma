package com.br.pruma.core.repository;

import com.br.pruma.core.domain.Inspecao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspecaoRepository extends JpaRepository<Inspecao, Integer> {

    List<Inspecao> findAllByProjeto_Id(Integer projetoId);

}
