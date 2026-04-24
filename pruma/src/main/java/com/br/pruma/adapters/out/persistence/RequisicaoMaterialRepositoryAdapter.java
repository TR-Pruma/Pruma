package com.br.pruma.adapters.out.persistence;

import com.br.pruma.core.domain.RequisicaoMaterial;
import com.br.pruma.core.repository.RequisicaoMaterialRepository;
import com.br.pruma.core.repository.port.RequisicaoMaterialRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adapter de saída (Output Adapter) que implementa o {@link RequisicaoMaterialRepositoryPort}
 * delegando as operações ao {@link RequisicaoMaterialRepository} do Spring Data JPA.
 */
@Component
@RequiredArgsConstructor
public class RequisicaoMaterialRepositoryAdapter implements RequisicaoMaterialRepositoryPort {

    private final RequisicaoMaterialRepository requisicaoMaterialRepository;

    @Override
    public RequisicaoMaterial save(RequisicaoMaterial requisicaoMaterial) {
        return requisicaoMaterialRepository.save(requisicaoMaterial);
    }

    @Override
    public Optional<RequisicaoMaterial> findById(Integer id) {
        return requisicaoMaterialRepository.findById(id);
    }

    @Override
    public List<RequisicaoMaterial> findAll() {
        return requisicaoMaterialRepository.findAll();
    }

    @Override
    public Page<RequisicaoMaterial> findAll(Pageable pageable) {
        return requisicaoMaterialRepository.findAll(pageable);
    }

    @Override
    public List<RequisicaoMaterial> findByProjetoId(Integer obraId) {
        return requisicaoMaterialRepository.findByObraId(obraId);
    }

    @Override
    public List<RequisicaoMaterial> findByMaterialId(Integer materialId) {
        return requisicaoMaterialRepository.findByMaterialId(materialId);
    }

    @Override
    public void deleteById(Integer id) {
        requisicaoMaterialRepository.deleteById(id);
    }

    @Override
    public void delete(RequisicaoMaterial requisicaoMaterial) {
        requisicaoMaterialRepository.delete(requisicaoMaterial);
    }

    @Override
    public boolean existsById(Integer id) {
        return requisicaoMaterialRepository.existsById(id);
    }
}
