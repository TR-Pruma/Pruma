package com.br.pruma.core.repository.port;

import com.br.pruma.core.domain.ImagemProjeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Port de saída (Output Port) para persistência de ImagemProjeto.
 */
public interface ImagemProjetoRepositoryPort {

    ImagemProjeto save(ImagemProjeto imagemProjeto);

    Optional<ImagemProjeto> findById(Integer id);

    List<ImagemProjeto> findAll();

    Page<ImagemProjeto> findAll(Pageable pageable);

    /** Busca todas as imagens de um projeto. */
    List<ImagemProjeto> findAllByProjeto_Id(Integer projetoId);

    void deleteById(Integer id);

    void delete(ImagemProjeto imagemProjeto);

    boolean existsById(Integer id);
}
