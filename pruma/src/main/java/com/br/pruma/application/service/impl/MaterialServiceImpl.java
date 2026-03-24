package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.MaterialRequestDTO;
import com.br.pruma.application.dto.response.MaterialResponseDTO;
import com.br.pruma.application.service.MaterialService;
import com.br.pruma.core.domain.Material;
import com.br.pruma.core.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository materialRepository;

    @Override
    @Transactional
    public MaterialResponseDTO criar(MaterialRequestDTO request) {
        Material entity = Material.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .unidade(request.unidade())
                .valorUnitario(request.valorUnitario())
                .build();
        return toResponse(materialRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialResponseDTO> listarTodos() {
        return materialRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialResponseDTO buscarPorId(Integer id) {
        return materialRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Material não encontrado: " + id));
    }

    @Override
    @Transactional
    public MaterialResponseDTO atualizar(Integer id, MaterialRequestDTO request) {
        Material entity = materialRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material não encontrado: " + id));
        entity.setNome(request.nome());
        entity.setDescricao(request.descricao());
        entity.setUnidade(request.unidade());
        entity.setValorUnitario(request.valorUnitario());
        return toResponse(materialRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!materialRepository.existsById(id)) {
            throw new EntityNotFoundException("Material não encontrado: " + id);
        }
        materialRepository.deleteById(id);
    }

    private MaterialResponseDTO toResponse(Material e) {
        return new MaterialResponseDTO(
                e.getId(),
                e.getNome(),
                e.getDescricao(),
                e.getUnidade(),
                e.getValorUnitario(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
