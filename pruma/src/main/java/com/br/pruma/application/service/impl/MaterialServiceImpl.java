package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.MaterialRequestDTO;
import com.br.pruma.application.dto.response.MaterialResponseDTO;
import com.br.pruma.application.dto.update.MaterialUpdateDTO;
import com.br.pruma.application.mapper.MaterialMapper;
import com.br.pruma.application.service.MaterialService;
import com.br.pruma.core.domain.Material;
import com.br.pruma.core.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialServiceImpl implements MaterialService {

    private final MaterialRepository repository;
    private final MaterialMapper mapper;

    @Override
    public MaterialResponseDTO create(MaterialRequestDTO dto) {
        repository.findByDescricao(dto.getDescricao()).ifPresent(m -> {
            throw new IllegalArgumentException("Ja existe material com essa descricao: " + dto.getDescricao());
        });
        Material entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaterialResponseDTO> listAll() {
        return repository.findAllByOrderByDescricaoAsc().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MaterialResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public MaterialResponseDTO update(Integer id, MaterialUpdateDTO dto) {
        Material entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material não encontrado: " + id));
        if (dto.getDescricao() != null) {
            repository.findByDescricao(dto.getDescricao()).ifPresent(found -> {
                if (!found.getId().equals(entity.getId())) {
                    throw new IllegalArgumentException("Outro material ja usa essa descricao: " + dto.getDescricao());
                }
            });
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public MaterialResponseDTO replace(Integer id, MaterialRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material não encontrado: " + id));
        Material entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Material não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
