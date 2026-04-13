package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Categoria;
import com.br.pruma.core.repository.CategoriaRepository;
import com.br.pruma.core.repository.port.CategoriaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link CategoriaRepositoryPort}
 * delegando ao {@link CategoriaRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class CategoriaRepositoryAdapter implements CategoriaRepositoryPort {

    private final CategoriaRepository categoriaRepository;

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Optional<Categoria> findById(Integer id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Page<Categoria> findAll(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    @Override
    public List<Categoria> findByNomeContainingIgnoreCase(String nome) {
        return categoriaRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public void deleteById(Integer id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public void delete(Categoria categoria) {
        categoriaRepository.delete(categoria);
    }

    @Override
    public boolean existsById(Integer id) {
        return categoriaRepository.existsById(id);
    }
}
