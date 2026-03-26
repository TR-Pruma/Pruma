package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.StatusEquipamentoRequestDTO;
import com.br.pruma.application.dto.response.StatusEquipamentoResponseDTO;
import com.br.pruma.application.dto.update.StatusEquipamentoUpdateDTO;
import com.br.pruma.application.mapper.StatusEquipamentoMapper;
import com.br.pruma.core.domain.StatusEquipamento;
import com.br.pruma.core.repository.StatusEquipamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusEquipamentoService {

    private final StatusEquipamentoRepository repository;
    private final StatusEquipamentoMapper mapper;

    public StatusEquipamentoResponseDTO create(StatusEquipamentoRequestDTO dto) {
        StatusEquipamento entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public StatusEquipamentoResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status de Equipamento não encontrado: " + id)));
    }

    @Transactional(readOnly = true)
    public List<StatusEquipamentoResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<StatusEquipamentoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public StatusEquipamentoResponseDTO update(Integer id, StatusEquipamentoUpdateDTO dto) {
        StatusEquipamento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status de Equipamento não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public StatusEquipamentoResponseDTO replace(Integer id, StatusEquipamentoRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status de Equipamento não encontrado: " + id));
        StatusEquipamento entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Status de Equipamento não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
