package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.StatusEquipamentoRequestDTO;
import com.br.pruma.application.dto.response.StatusEquipamentoResponseDTO;
import com.br.pruma.application.dto.update.StatusEquipamentoUpdateDTO;
import com.br.pruma.application.mapper.StatusEquipamentoMapper;
import com.br.pruma.application.service.StatusEquipamentoService;
import com.br.pruma.config.Constantes;
import com.br.pruma.core.domain.StatusEquipamento;
import com.br.pruma.core.repository.port.StatusEquipamentoRepositoryPort;
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

    private final StatusEquipamentoRepositoryPort statusEquipamentoRepositoryPort;
    private final StatusEquipamentoMapper statusEquipamentoMapper;

    @Override
    public StatusEquipamentoResponseDTO create(StatusEquipamentoRequestDTO dto) {
        StatusEquipamento statusEquipamento = statusEquipamentoMapper.toEntity(dto);
        StatusEquipamento saved = statusEquipamentoRepositoryPort.save(statusEquipamento);
        return statusEquipamentoMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public StatusEquipamentoResponseDTO getById(Integer id) {
        StatusEquipamento statusEquipamento = statusEquipamentoRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.STATUS_EQUIPAMENTO_NAO_ENCONTRADO + id));
        return statusEquipamentoMapper.toResponse(statusEquipamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatusEquipamentoResponseDTO> listAll() {
        return statusEquipamentoRepositoryPort.findAll()
                .stream()
                .map(statusEquipamentoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatusEquipamentoResponseDTO> list(Pageable pageable) {
        return statusEquipamentoRepositoryPort.findAll(pageable)
                .map(statusEquipamentoMapper::toResponse);
    }

    @Override
    public StatusEquipamentoResponseDTO update(Integer id, StatusEquipamentoUpdateDTO dto) {
        StatusEquipamento statusEquipamento = statusEquipamentoRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.STATUS_EQUIPAMENTO_NAO_ENCONTRADO + id));
        statusEquipamentoMapper.updateFromDto(dto, statusEquipamento);
        StatusEquipamento updated = statusEquipamentoRepositoryPort.save(statusEquipamento);
        return statusEquipamentoMapper.toResponse(updated);
    }

    @Override
    public StatusEquipamentoResponseDTO replace(Integer id, StatusEquipamentoRequestDTO dto) {
        statusEquipamentoRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.STATUS_EQUIPAMENTO_NAO_ENCONTRADO + id));
        StatusEquipamento statusEquipamento = statusEquipamentoMapper.toEntity(dto);
        statusEquipamento.setId(id);
        StatusEquipamento replaced = statusEquipamentoRepositoryPort.save(statusEquipamento);
        return statusEquipamentoMapper.toResponse(replaced);
    }

    @Override
    public void delete(Integer id) {
        StatusEquipamento statusEquipamento = statusEquipamentoRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.STATUS_EQUIPAMENTO_NAO_ENCONTRADO + id));
        statusEquipamento.setAtivo(false);
        statusEquipamentoRepositoryPort.save(statusEquipamento);
    }
}
