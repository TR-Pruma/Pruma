package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.MaterialRequestDTO;
import com.br.pruma.application.dto.response.MaterialResponseDTO;
import com.br.pruma.application.dto.update.MaterialUpdateDTO;
import com.br.pruma.application.mapper.MaterialMapper;
import com.br.pruma.core.domain.Material;
import com.br.pruma.core.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialService {

    private final MaterialRepository repository;
    private final MaterialMapper mapper;

    public MaterialResponseDTO create(MaterialRequestDTO dto) {
        Material entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public MaterialResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material não encontrado: " + id)));
    }

    @Transactional(readOnly = true)
    public List<MaterialResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<MaterialResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public MaterialResponseDTO update(Integer id, MaterialUpdateDTO dto) {
        Material entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public MaterialResponseDTO replace(Integer id, MaterialRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Material não encontrado: " + id));
        Material entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Material não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
