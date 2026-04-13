package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.ImagemProjeto;
import com.br.pruma.core.repository.ImagemProjetoRepository;
import com.br.pruma.core.repository.port.ImagemProjetoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link ImagemProjetoRepositoryPort}
 * delegando ao {@link ImagemProjetoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class ImagemProjetoRepositoryAdapter implements ImagemProjetoRepositoryPort {

    private final ImagemProjetoRepository imagemProjetoRepository;

    @Override
    public ImagemProjeto save(ImagemProjeto imagemProjeto) {
        return imagemProjetoRepository.save(imagemProjeto);
    }

    @Override
    public Optional<ImagemProjeto> findById(Integer id) {
        return imagemProjetoRepository.findById(id);
    }

    @Override
    public List<ImagemProjeto> findAll() {
        return imagemProjetoRepository.findAll();
    }

    @Override
    public Page<ImagemProjeto> findAll(Pageable pageable) {
        return imagemProjetoRepository.findAll(pageable);
    }

    @Override
    public List<ImagemProjeto> findAllByProjeto_Id(Integer projetoId) {
        return imagemProjetoRepository.findAllByProjeto_Id(projetoId);
    }

    @Override
    public void deleteById(Integer id) {
        imagemProjetoRepository.deleteById(id);
    }

    @Override
    public void delete(ImagemProjeto imagemProjeto) {
        imagemProjetoRepository.delete(imagemProjeto);
    }

    @Override
    public boolean existsById(Integer id) {
        return imagemProjetoRepository.existsById(id);
    }
}
