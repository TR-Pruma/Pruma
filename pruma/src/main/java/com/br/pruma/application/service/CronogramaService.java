package com.br.pruma.application.service;


import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import com.br.pruma.application.mapper.CronogramaMapper;
import com.br.pruma.config.ResourceNotFoundException;
import com.br.pruma.core.domain.Cronograma;
import com.br.pruma.core.repository.CronogramaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CronogramaService {

    private final CronogramaRepository repository;
    private final CronogramaMapper mapper;

    @Transactional
    public CronogramaResponseDTO create(CronogramaRequestDTO dto) {
        Cronograma entity = mapper.toEntity(dto);
        Cronograma saved = repository.save(entity);
        return mapper.toResponse(saved);
    }
    @Transactional(readOnly = true)
    public CronogramaResponseDTO getById(Integer id) {
        Cronograma entity = findOrThrow(id);
        return mapper.toResponse(entity);
    }
    @Transactional(readOnly = true)
    public List<CronogramaResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
    @Transactional
    public CronogramaResponseDTO update(Integer id, Integer integer, CronogramaRequestDTO dto) {
        Cronograma entity = findOrThrow(id);
        mapper.updateEntityFromDto(dto, entity);
        Cronograma updated = repository.save(entity);
        return mapper.toResponse(updated);
    }
    @Transactional
    public void delete(Integer id, Integer integer) {
        Cronograma entity = findOrThrow(id);
        repository.delete(entity);
    }
    private Cronograma findOrThrow(Integer id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Cronograma não encontrado com id " + id));
    }
    @Transactional(readOnly = true)
    public CronogramaResponseDTO getByIdAndProjeto(Integer projetoId, Integer id) {
        Cronograma entity = repository
                .findByIdAndProjetoId(id, projetoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cronograma não encontrado (id=" + id + ", projetoId=" + projetoId + ")"
                        )
                );
        return mapper.toResponse(entity);
    }    @Transactional(readOnly = true)
    public List<CronogramaResponseDTO> getAllByProjeto(Integer projetoId) {
        return repository.findAllByProjetoId(projetoId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }




}
