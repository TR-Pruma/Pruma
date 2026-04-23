package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.PreObraRequestDTO;
import com.br.pruma.application.dto.response.PreObraResponseDTO;
import com.br.pruma.application.dto.update.PreObraUpdateDTO;
import com.br.pruma.application.mapper.PreObraMapper;
import com.br.pruma.application.service.PreObraService;
import com.br.pruma.config.Constantes;
import com.br.pruma.core.domain.PreObra;
import com.br.pruma.core.repository.port.PreObraRepositoryPort;
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
public class PreObraServiceImpl implements PreObraService {

    private final PreObraRepositoryPort preObraRepositoryPort;
    private final PreObraMapper preObraMapper;

    @Override
    public PreObraResponseDTO create(PreObraRequestDTO dto) {
        PreObra preObra = preObraMapper.toEntity(dto);
        PreObra saved = preObraRepositoryPort.save(preObra);
        return preObraMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PreObraResponseDTO getById(Integer id) {
        PreObra preObra = preObraRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.PRE_OBRA_NAO_ENCONTRADA + id));
        return preObraMapper.toResponse(preObra);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreObraResponseDTO> listAll() {
        return preObraRepositoryPort.findAll()
                .stream()
                .map(preObraMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PreObraResponseDTO> list(Pageable pageable) {
        return preObraRepositoryPort.findAll(pageable)
                .map(preObraMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreObraResponseDTO> listByProjeto(Integer projetoId) {
        return preObraRepositoryPort.findAllByObra_Id(projetoId)
                .stream()
                .map(preObraMapper::toResponse)
                .toList();
    }

    @Override
    public PreObraResponseDTO update(Integer id, PreObraUpdateDTO dto) {
        PreObra preObra = preObraRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.PRE_OBRA_NAO_ENCONTRADA + id));
        preObraMapper.updateFromDto(dto, preObra);
        PreObra updated = preObraRepositoryPort.save(preObra);
        return preObraMapper.toResponse(updated);
    }

    @Override
    public PreObraResponseDTO replace(Integer id, PreObraRequestDTO dto) {
        preObraRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.PRE_OBRA_NAO_ENCONTRADA + id));
        PreObra preObra = preObraMapper.toEntity(dto);
        preObra.setId(id);
        PreObra replaced = preObraRepositoryPort.save(preObra);
        return preObraMapper.toResponse(replaced);
    }

    @Override
    public void delete(Integer id) {
        PreObra preObra = preObraRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.PRE_OBRA_NAO_ENCONTRADA + id));
        preObraRepositoryPort.delete(preObra);
    }
}
