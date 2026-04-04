package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoResponseDTO;
import com.br.pruma.application.dto.update.EquipamentoUpdateDTO;
import com.br.pruma.application.mapper.EquipamentoMapper;
import com.br.pruma.application.service.EquipamentoService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Equipamento;
import com.br.pruma.core.repository.EquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipamentoServiceImpl implements EquipamentoService {

    private final EquipamentoRepository repository;
    private final EquipamentoMapper mapper;

    @Override
    public EquipamentoResponseDTO create(EquipamentoRequestDTO dto) {
        Equipamento entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public EquipamentoResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Equipamento com ID " + id + " não encontrado.")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipamentoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipamentoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public EquipamentoResponseDTO update(Integer id, EquipamentoUpdateDTO dto) {
        Equipamento entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Equipamento com ID " + id + " não encontrado."));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public EquipamentoResponseDTO replace(Integer id, EquipamentoRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Equipamento com ID " + id + " não encontrado."));
        Equipamento entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        Equipamento entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Equipamento com ID " + id + " não encontrado."));
        repository.delete(entity);
    }
}
