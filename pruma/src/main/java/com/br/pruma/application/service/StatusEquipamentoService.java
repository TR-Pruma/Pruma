package com.br.pruma.application.service;


import com.br.pruma.application.dto.request.StatusEquipamentoRequestDTO;
import com.br.pruma.application.dto.response.StatusEquipamentoResponseDTO;
import com.br.pruma.application.mapper.StatusEquipamentoMapper;
import com.br.pruma.core.domain.StatusEquipamento;
import com.br.pruma.core.repository.StatusEquipamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusEquipamentoService {

    private final StatusEquipamentoRepository repository;
    private final StatusEquipamentoMapper mapper;

    @Transactional(readOnly = true)
    public List<StatusEquipamentoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public StatusEquipamentoResponseDTO getById(Integer id) {
        StatusEquipamento entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status de equipamento não encontrado com id " + id));
        return mapper.toDTO(entity);
    }

    @Transactional(readOnly = true)
    public StatusEquipamentoResponseDTO getByDescricao(String descricao) {
        StatusEquipamento entity = repository.findByDescricao(descricao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status de equipamento não encontrado com descrição " + descricao));
        return mapper.toDTO(entity);
    }

    @Transactional
    public StatusEquipamentoResponseDTO create(StatusEquipamentoRequestDTO request) {
        StatusEquipamento entity = mapper.toEntity(request);
        StatusEquipamento saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Transactional
    public StatusEquipamentoResponseDTO update(Integer id, StatusEquipamentoRequestDTO request) {
        StatusEquipamento existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status de equipamento não encontrado com id " + id));
        mapper.updateFromDto(request, existing);
        StatusEquipamento saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status de equipamento não encontrado com id " + id);
        }
        repository.deleteById(id);
    }
}