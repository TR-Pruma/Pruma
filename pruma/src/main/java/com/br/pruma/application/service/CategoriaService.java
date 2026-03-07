package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.CategoriaRequestDTO;
import com.br.pruma.application.dto.response.CategoriaResponseDTO;
import com.br.pruma.application.dto.update.CategoriaUpdateDTO;
import com.br.pruma.application.mapper.CategoriaMapper;
import com.br.pruma.core.domain.Categoria;
import com.br.pruma.core.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaService {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;

    public CategoriaResponseDTO create(CategoriaRequestDTO dto) {
        Categoria entity = mapper.toEntity(dto);
        Categoria saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO getById(Integer id) {
        Categoria entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada: " + id));
        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public CategoriaResponseDTO update(Integer id, CategoriaUpdateDTO dto) {
        Categoria entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada: " + id));
        mapper.updateFromDto(dto, entity);
        Categoria updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Categoria não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
