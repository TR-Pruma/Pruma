package com.br.pruma.core.repository;

import com.br.pruma.core.domain.ImagemProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemProjetoRepository extends JpaRepository<ImagemProjeto, Integer> {

    List<ImagemProjeto> findAllByProjeto_Id(Integer projetoId);

}
