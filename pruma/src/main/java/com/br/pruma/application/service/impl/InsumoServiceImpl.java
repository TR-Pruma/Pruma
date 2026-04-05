package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.InsumoRequestDTO;
import com.br.pruma.application.dto.response.InsumoResponseDTO;
import com.br.pruma.application.mapper.InsumoMapper;
import com.br.pruma.application.service.InsumoService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Insumo;
import com.br.pruma.core.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InsumoServiceImpl implements InsumoService {

    private final InsumoRepository repository;
    private final InsumoMapper mapper;

    @Override
    public InsumoResponseDTO create(InsumoRequestDTO dto) {
        Insumo insumo = mapper.toEntity(dto);
        return mapper.toDto(repository.save(insumo));
    }

    @Override
    @Transactional(readOnly = true)
    public InsumoResponseDTO getById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Insumo com ID " + id + " não encontrado."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InsumoResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public InsumoResponseDTO update(Integer id, InsumoRequestDTO dto) {
        Insumo insumo = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Insumo com ID " + id + " não encontrado."));
        mapper.updateFromDto(dto, insumo);
        return mapper.toDto(repository.save(insumo));
    }

    @Override
    public void delete(Integer id) {
        Insumo entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Insumo com ID " + id + " não encontrado."));
        repository.delete(entity);
    }
}
