package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.MaterialUtilizadoRequestDTO;
import com.br.pruma.application.dto.response.MaterialUtilizadoResponseDTO;
import com.br.pruma.application.dto.update.MaterialUtilizadoUpdateDTO;
import com.br.pruma.application.service.MaterialUtilizadoService;
import com.br.pruma.core.domain.MaterialUtilizado;
import com.br.pruma.core.exception.NotFoundException;
import com.br.pruma.core.mapper.MaterialUtilizadoMapper;
import com.br.pruma.core.port.out.MaterialUtilizadoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialUtilizadoServiceImpl implements MaterialUtilizadoService {

    private final MaterialUtilizadoRepositoryPort repositoryPort;
    private final MaterialUtilizadoMapper mapper;

    @Override
    @Transactional
    public MaterialUtilizadoResponseDTO create(MaterialUtilizadoRequestDTO dto) {
        MaterialUtilizado entity = mapper.toEntity(dto);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    public MaterialUtilizadoResponseDTO getById(Integer id) {
        return mapper.toResponse(findOrThrow(id));
    }

    @Override
    public List<MaterialUtilizadoResponseDTO> listAll() {
        return repositoryPort.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public Page<MaterialUtilizadoResponseDTO> list(Pageable pageable) {
        return repositoryPort.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public List<MaterialUtilizadoResponseDTO> listByProjeto(Integer projetoId) {
        return repositoryPort.findByProjetoId(projetoId).stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional
    public MaterialUtilizadoResponseDTO update(Integer id, MaterialUtilizadoUpdateDTO dto) {
        MaterialUtilizado entity = findOrThrow(id);
        mapper.updateEntity(dto, entity);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    @Transactional
    public MaterialUtilizadoResponseDTO replace(Integer id, MaterialUtilizadoRequestDTO dto) {
        findOrThrow(id);
        MaterialUtilizado entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        findOrThrow(id);
        repositoryPort.deleteById(id);
    }

    private MaterialUtilizado findOrThrow(Integer id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("MaterialUtilizado não encontrado: " + id));
    }
}
