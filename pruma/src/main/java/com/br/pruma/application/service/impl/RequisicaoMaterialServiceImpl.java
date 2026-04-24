package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.RequisicaoMaterialRequestDTO;
import com.br.pruma.application.dto.response.RequisicaoMaterialResponseDTO;
import com.br.pruma.application.dto.update.RequisicaoMaterialUpdateDTO;
import com.br.pruma.application.mapper.RequisicaoMaterialMapper;
import com.br.pruma.application.service.RequisicaoMaterialService;
import com.br.pruma.core.domain.RequisicaoMaterial;
import com.br.pruma.core.exception.RecursoNaoEncontradoException;
import com.br.pruma.core.repository.port.RequisicaoMaterialRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequisicaoMaterialServiceImpl implements RequisicaoMaterialService {

    private final RequisicaoMaterialRepositoryPort repositoryPort;
    private final RequisicaoMaterialMapper mapper;

    @Override
    @Transactional
    public RequisicaoMaterialResponseDTO create(RequisicaoMaterialRequestDTO dto) {
        RequisicaoMaterial entity = mapper.toEntity(dto);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    public RequisicaoMaterialResponseDTO getById(Integer id) {
        return mapper.toResponse(findOrThrow(id));
    }

    @Override
    public List<RequisicaoMaterialResponseDTO> listAll() {
        return repositoryPort.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public Page<RequisicaoMaterialResponseDTO> list(Pageable pageable) {
        return repositoryPort.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public List<RequisicaoMaterialResponseDTO> listByProjeto(Integer projetoId) {
        return repositoryPort.findByProjetoId(projetoId).stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional
    public RequisicaoMaterialResponseDTO update(Integer id, RequisicaoMaterialUpdateDTO dto) {
        RequisicaoMaterial entity = findOrThrow(id);
        mapper.updateEntity(dto, entity);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    @Transactional
    public RequisicaoMaterialResponseDTO replace(Integer id, RequisicaoMaterialRequestDTO dto) {
        findOrThrow(id);
        RequisicaoMaterial entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        findOrThrow(id);
        repositoryPort.deleteById(id);
    }

    private RequisicaoMaterial findOrThrow(Integer id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("RequisicaoMaterial não encontrada: " + id));
    }
}
