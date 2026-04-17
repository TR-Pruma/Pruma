package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ProfissionalDeBaseRequestDTO;
import com.br.pruma.application.dto.response.ProfissionalDeBaseResponseDTO;
import com.br.pruma.application.dto.update.ProfissionalDeBaseUpdateDTO;
import com.br.pruma.application.mapper.ProfissionalDeBaseMapper;
import com.br.pruma.application.service.ProfissionalDeBaseService;
import com.br.pruma.core.domain.ProfissionalDeBase;
import com.br.pruma.core.repository.port.ProfissionalDeBaseRepositoryPort;
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
public class ProfissionalDeBaseServiceImpl implements ProfissionalDeBaseService {

    private final ProfissionalDeBaseRepositoryPort profissionalDeBaseRepositoryPort;
    private final ProfissionalDeBaseMapper profissionalDeBaseMapper;

    @Override
    public ProfissionalDeBaseResponseDTO create(ProfissionalDeBaseRequestDTO dto) {
        ProfissionalDeBase entity = profissionalDeBaseMapper.toEntity(dto);
        ProfissionalDeBase saved = profissionalDeBaseRepositoryPort.save(entity);
        return profissionalDeBaseMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfissionalDeBaseResponseDTO getById(Integer id) {
        ProfissionalDeBase entity = profissionalDeBaseRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProfissionalDeBase não encontrado: " + id));
        return profissionalDeBaseMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfissionalDeBaseResponseDTO> listAll() {
        return profissionalDeBaseRepositoryPort.findAll()
                .stream()
                .map(profissionalDeBaseMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProfissionalDeBaseResponseDTO> list(Pageable pageable) {
        return profissionalDeBaseRepositoryPort.findAll(pageable)
                .map(profissionalDeBaseMapper::toResponse);
    }

    @Override
    public ProfissionalDeBaseResponseDTO update(Integer id, ProfissionalDeBaseUpdateDTO dto) {
        ProfissionalDeBase entity = profissionalDeBaseRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProfissionalDeBase não encontrado: " + id));
        profissionalDeBaseMapper.updateFromDto(dto, entity);
        ProfissionalDeBase updated = profissionalDeBaseRepositoryPort.save(entity);
        return profissionalDeBaseMapper.toResponse(updated);
    }

    @Override
    public ProfissionalDeBaseResponseDTO replace(Integer id, ProfissionalDeBaseRequestDTO dto) {
        profissionalDeBaseRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProfissionalDeBase não encontrado: " + id));
        ProfissionalDeBase entity = profissionalDeBaseMapper.toEntity(dto);
        entity.setId(id);
        ProfissionalDeBase replaced = profissionalDeBaseRepositoryPort.save(entity);
        return profissionalDeBaseMapper.toResponse(replaced);
    }

    @Override
    public void delete(Integer id) {
        ProfissionalDeBase entity = profissionalDeBaseRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProfissionalDeBase não encontrado: " + id));
        profissionalDeBaseRepositoryPort.delete(entity);
    }
}