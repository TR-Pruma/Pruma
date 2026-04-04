package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.CategoriaRequestDTO;
import com.br.pruma.application.dto.response.CategoriaResponseDTO;
import com.br.pruma.application.dto.update.CategoriaUpdateDTO;
import com.br.pruma.application.mapper.CategoriaMapper;
import com.br.pruma.application.service.CategoriaService;
import com.br.pruma.core.domain.Categoria;
import com.br.pruma.core.repository.CategoriaRepository;
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
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;

    @Override
    public CategoriaResponseDTO create(CategoriaRequestDTO dto) {
        Categoria entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoriaResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public CategoriaResponseDTO update(Integer id, CategoriaUpdateDTO dto) {
        Categoria entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public CategoriaResponseDTO replace(Integer id, CategoriaRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada: " + id));
        Categoria entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Categoria não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
