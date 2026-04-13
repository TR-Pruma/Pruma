package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.Material;
import com.br.pruma.core.repository.MaterialRepository;
import com.br.pruma.core.repository.port.MaterialRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link MaterialRepositoryPort}
 * delegando ao {@link MaterialRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class MaterialRepositoryAdapter implements MaterialRepositoryPort {

    private final MaterialRepository materialRepository;

    @Override
    public Material save(Material material) {
        return materialRepository.save(material);
    }

    @Override
    public Optional<Material> findById(Integer id) {
        return materialRepository.findById(id);
    }

    @Override
    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    @Override
    public Page<Material> findAll(Pageable pageable) {
        return materialRepository.findAll(pageable);
    }

    @Override
    public Optional<Material> findByDescricao(String descricao) {
        return materialRepository.findByDescricao(descricao);
    }

    @Override
    public List<Material> findAllByOrderByDescricaoAsc() {
        return materialRepository.findAllByOrderByDescricaoAsc();
    }

    @Override
    public void deleteById(Integer id) {
        materialRepository.deleteById(id);
    }

    @Override
    public void delete(Material material) {
        materialRepository.delete(material);
    }

    @Override
    public boolean existsById(Integer id) {
        return materialRepository.existsById(id);
    }
}
