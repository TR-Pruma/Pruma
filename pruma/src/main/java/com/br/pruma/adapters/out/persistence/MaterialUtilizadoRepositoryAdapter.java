package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.MaterialUtilizado;
import com.br.pruma.core.repository.MaterialUtilizadoRepository;
import com.br.pruma.core.repository.port.MaterialUtilizadoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída que implementa {@link MaterialUtilizadoRepositoryPort}
 * delegando ao {@link MaterialUtilizadoRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class MaterialUtilizadoRepositoryAdapter implements MaterialUtilizadoRepositoryPort {

    private final MaterialUtilizadoRepository materialUtilizadoRepository;

    @Override
    public MaterialUtilizado save(MaterialUtilizado materialUtilizado) {
        return materialUtilizadoRepository.save(materialUtilizado);
    }

    @Override
    public Optional<MaterialUtilizado> findById(Integer id) {
        return materialUtilizadoRepository.findById(id);
    }

    @Override
    public List<MaterialUtilizado> findAll() {
        return materialUtilizadoRepository.findAll();
    }

    @Override
    public Page<MaterialUtilizado> findAll(Pageable pageable) {
        return materialUtilizadoRepository.findAll(pageable);
    }

    @Override
    public List<MaterialUtilizado> findByProjetoId(Integer projetoId) {
        return materialUtilizadoRepository.findAllByAtividade_Projeto_Id(projetoId);
    }

    @Override
    public void deleteById(Integer id) {
        materialUtilizadoRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return materialUtilizadoRepository.existsById(id);
    }
}
