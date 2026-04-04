package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;
import com.br.pruma.application.dto.update.EmpresaUpdateDTO;
import com.br.pruma.application.mapper.EmpresaMapper;
import com.br.pruma.application.service.EmpresaService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Empresa;
import com.br.pruma.core.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository repository;
    private final EmpresaMapper mapper;

    @Override
    public EmpresaResponseDTO create(EmpresaRequestDTO dto) {
        Empresa entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public EmpresaResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Empresa com ID " + id + " não encontrada.")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpresaResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmpresaResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public EmpresaResponseDTO update(Integer id, EmpresaUpdateDTO dto) {
        Empresa entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Empresa com ID " + id + " não encontrada."));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public EmpresaResponseDTO replace(Integer id, EmpresaRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Empresa com ID " + id + " não encontrada."));
        Empresa entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        Empresa entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Empresa com ID " + id + " não encontrada."));
        repository.delete(entity);
    }
}
