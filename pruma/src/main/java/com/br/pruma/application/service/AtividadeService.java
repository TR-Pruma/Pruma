package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.AtividadeRequestDTO;
import com.br.pruma.application.dto.response.AtividadeResponseDTO;
import com.br.pruma.application.dto.update.AtividadeUpdateDTO;
import com.br.pruma.application.mapper.AtividadeMapper;
import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.repository.AtividadeRepository;
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
public class AtividadeService {

    private final AtividadeRepository repository;
    private final AtividadeMapper mapper;

    public AtividadeResponseDTO create(AtividadeRequestDTO dto) {
        Atividade entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public AtividadeResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada: " + id)));
    }

    @Transactional(readOnly = true)
    public List<AtividadeResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<AtividadeResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public AtividadeResponseDTO update(Integer id, AtividadeUpdateDTO dto) {
        Atividade entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public AtividadeResponseDTO replace(Integer id, AtividadeRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada: " + id));
        Atividade entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Atividade não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
