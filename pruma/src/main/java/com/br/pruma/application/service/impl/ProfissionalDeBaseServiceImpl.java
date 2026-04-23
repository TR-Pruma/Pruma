package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ProfissionalDeBaseRequestDTO;
import com.br.pruma.application.dto.response.ProfissionalDeBaseResponseDTO;
import com.br.pruma.application.dto.update.ProfissionalDeBaseUpdateDTO;
import com.br.pruma.application.mapper.ProfissionalDeBaseMapper;
import com.br.pruma.application.service.ProfissionalDeBaseService;
import com.br.pruma.config.Constantes;
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
        ProfissionalDeBase profissionalDeBase = profissionalDeBaseMapper.toEntity(dto);
        ProfissionalDeBase saved = profissionalDeBaseRepositoryPort.save(profissionalDeBase);
        return profissionalDeBaseMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfissionalDeBaseResponseDTO getById(Integer id) {
        ProfissionalDeBase profissionalDeBase = profissionalDeBaseRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.PROFISSIONAL_DE_BASE_NAO_ENCONTRADO + id));
        return profissionalDeBaseMapper.toResponse(profissionalDeBase);
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
        ProfissionalDeBase profissionalDeBase = profissionalDeBaseRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.PROFISSIONAL_DE_BASE_NAO_ENCONTRADO + id));
        profissionalDeBaseMapper.updateFromDto(dto, profissionalDeBase);
        ProfissionalDeBase updated = profissionalDeBaseRepositoryPort.save(profissionalDeBase);
        return profissionalDeBaseMapper.toResponse(updated);
    }

    @Override
    public ProfissionalDeBaseResponseDTO replace(Integer id, ProfissionalDeBaseRequestDTO dto) {
        profissionalDeBaseRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.PROFISSIONAL_DE_BASE_NAO_ENCONTRADO + id));
        ProfissionalDeBase profissionalDeBase = profissionalDeBaseMapper.toEntity(dto);
        profissionalDeBase.setId(id);
        ProfissionalDeBase replaced = profissionalDeBaseRepositoryPort.save(profissionalDeBase);
        return profissionalDeBaseMapper.toResponse(replaced);
    }

    @Override
    public void delete(Integer id) {
        ProfissionalDeBase profissionalDeBase = profissionalDeBaseRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.PROFISSIONAL_DE_BASE_NAO_ENCONTRADO + id));
        profissionalDeBaseRepositoryPort.delete(profissionalDeBase);
    }
}
