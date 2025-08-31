package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.MaterialUtilizadoRequestDTO;
import com.br.pruma.application.dto.response.MaterialUtilizadoResponseDTO;
import com.br.pruma.application.dto.update.MaterialUtilizadoUpdateDTO;
import com.br.pruma.application.mapper.MaterialUtilizadoMapper;
import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.domain.Material;
import com.br.pruma.core.domain.MaterialUtilizado;
import com.br.pruma.core.repository.MaterialRepository;
import com.br.pruma.core.repository.MaterialUtilizadoRepository;
import com.br.pruma.infra.repository.AtividadeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialUtilizadoService {
    private final MaterialUtilizadoRepository repository;
    private final MaterialRepository materialRepository;
    private final AtividadeRepository atividadeRepository;
    private final MaterialUtilizadoMapper mapper;

    /**
     * Cria uma nova associação de material utilizado em atividade.
     */
    public MaterialUtilizadoResponseDTO create(MaterialUtilizadoRequestDTO dto) {
        Material material = materialRepository.findById(dto.getMaterialId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Material não encontrado: " + dto.getMaterialId())
                );
        Atividade atividade = atividadeRepository.findById(dto.getAtividadeId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Atividade não encontrada: " + dto.getAtividadeId())
                );

        MaterialUtilizado entity = mapper.toEntity(dto);
        // substitui stubs por entidades gerenciadas
        entity.setMaterial(material);
        entity.setAtividade(atividade);

        MaterialUtilizado saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Busca uma associação por ID.
     */
    @Transactional(readOnly = true)
    public MaterialUtilizadoResponseDTO getById(Integer id) {
        MaterialUtilizado entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("MaterialUtilizado não encontrado: " + id)
                );
        return mapper.toResponse(entity);
    }

    /**
     * Lista todas as associações de material utilizado.
     */
    @Transactional(readOnly = true)
    public List<MaterialUtilizadoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista todas as associações de material utilizado para uma atividade específica.
     */
    @Transactional(readOnly = true)
    public List<MaterialUtilizadoResponseDTO> listByAtividade(Integer atividadeId) {
        return repository.findAllByAtividade_Id(atividadeId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista todas as associações de material utilizado para um material específico.
     */
    @Transactional(readOnly = true)
    public List<MaterialUtilizadoResponseDTO> listByMaterial(Integer materialId) {
        return repository.findAllByMaterial_Id(materialId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza parcialmente uma associação existente.
     */
    public MaterialUtilizadoResponseDTO update(Integer id, MaterialUtilizadoUpdateDTO dto) {
        MaterialUtilizado entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("MaterialUtilizado não encontrado: " + id)
                );

        if (dto.getMaterialId() != null) {
            Material material = materialRepository.findById(dto.getMaterialId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Material não encontrado: " + dto.getMaterialId())
                    );
            entity.setMaterial(material);
        }

        if (dto.getAtividadeId() != null) {
            Atividade atividade = atividadeRepository.findById(dto.getAtividadeId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Atividade não encontrada: " + dto.getAtividadeId())
                    );
            entity.setAtividade(atividade);
        }

        mapper.updateFromDto(dto, entity);
        MaterialUtilizado updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Remove uma associação de material utilizado pelo seu ID.
     */
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("MaterialUtilizado não encontrado: " + id);
        }
        repository.deleteById(id);
    }

}