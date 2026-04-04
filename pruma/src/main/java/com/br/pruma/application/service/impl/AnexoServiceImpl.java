package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.AnexoRequestDTO;
import com.br.pruma.application.dto.response.AnexoResponseDTO;
import com.br.pruma.application.mapper.AnexoMapper;
import com.br.pruma.application.service.AnexoService;
import com.br.pruma.core.domain.Anexo;
import com.br.pruma.core.repository.AnexoRepository;
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
public class AnexoServiceImpl implements AnexoService {

    private final AnexoRepository repository;
    private final AnexoMapper mapper;

    @Override
    public AnexoResponseDTO create(AnexoRequestDTO dto) {
        Anexo entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public AnexoResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Anexo não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnexoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnexoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public AnexoResponseDTO update(Integer id, AnexoRequestDTO dto) {
        Anexo entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Anexo não encontrado: " + id));
        mapper.updateFromRequest(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        Anexo entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Anexo não encontrado: " + id));
        repository.delete(entity);
    }
}
