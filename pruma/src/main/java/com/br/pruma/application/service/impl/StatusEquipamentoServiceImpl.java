package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.StatusEquipamentoRequestDTO;
import com.br.pruma.application.dto.response.StatusEquipamentoResponseDTO;
import com.br.pruma.application.dto.update.StatusEquipamentoUpdateDTO;
import com.br.pruma.application.mapper.StatusEquipamentoMapper;
import com.br.pruma.application.service.StatusEquipamentoService;
import com.br.pruma.core.domain.StatusEquipamento;
import com.br.pruma.core.repository.StatusEquipamentoRepository;
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
public class StatusEquipamentoServiceImpl implements StatusEquipamentoService {

    private final StatusEquipamentoRepository repository;
    private final StatusEquipamentoMapper mapper;

    @Override
    public StatusEquipamentoResponseDTO create(StatusEquipamentoRequestDTO dto) {
        StatusEquipamento entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public StatusEquipamentoResponseDTO getById(Integer id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "StatusEquipamento não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatusEquipamentoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatusEquipamentoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public StatusEquipamentoResponseDTO update(Integer id, StatusEquipamentoUpdateDTO dto) {
        StatusEquipamento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "StatusEquipamento não encontrado: " + id));

        mapper.updateFromDto(dto, entity);

        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public StatusEquipamentoResponseDTO replace(Integer id, StatusEquipamentoRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "StatusEquipamento não encontrado: " + id));

        StatusEquipamento entity = mapper.toEntity(dto);
        entity.setId(id);

        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        StatusEquipamento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "StatusEquipamento não encontrado: " + id));
        entity.setAtivo(false);
        repository.save(entity);
    }
}