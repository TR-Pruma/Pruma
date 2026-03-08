package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.InsumoRequestDTO;
import com.br.pruma.application.dto.response.InsumoResponseDTO;
import com.br.pruma.application.mapper.InsumoMapper;
import com.br.pruma.application.service.InsumoService;
import com.br.pruma.core.domain.Insumo;
import com.br.pruma.core.repository.InsumoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InsumoServiceImpl implements InsumoService {

    private final InsumoRepository repository;
    private final InsumoMapper mapper;

    @Override
    public InsumoResponseDTO create(InsumoRequestDTO dto) {
        Insumo entity = mapper.toEntity(dto);
        Insumo saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public InsumoResponseDTO getById(Integer id) {
        Insumo entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo não encontrado: " + id));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InsumoResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public InsumoResponseDTO update(Integer id, InsumoRequestDTO dto) {
        Insumo entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Insumo não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toDto(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Insumo não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
