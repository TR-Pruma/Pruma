package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.AuditoriaRequestDTO;
import com.br.pruma.application.dto.response.AuditoriaResponseDTO;
import com.br.pruma.application.dto.update.AuditoriaUpdateDTO;
import com.br.pruma.application.mapper.AuditoriaMapper;
import com.br.pruma.application.service.AuditoriaService;
import com.br.pruma.core.domain.Auditoria;
import com.br.pruma.core.repository.AuditoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaRepository repository;
    private final AuditoriaMapper mapper;

    @Override
    public AuditoriaResponseDTO create(AuditoriaRequestDTO dto) {
        Auditoria entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public AuditoriaResponseDTO getById(UUID id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auditoria não encontrada: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditoriaResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditoriaResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public AuditoriaResponseDTO update(UUID id, AuditoriaUpdateDTO dto) {
        Auditoria entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auditoria não encontrada: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(UUID id) {
        Auditoria entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auditoria não encontrada: " + id));
        entity.setAtivo(false);
        repository.save(entity);
    }
}
