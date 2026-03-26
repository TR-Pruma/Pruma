package com.br.pruma.application.service;


import com.br.pruma.application.dto.request.SubContratoRequestDTO;
import com.br.pruma.application.dto.response.SubContratoResponseDTO;
import com.br.pruma.application.mapper.SubContratoMapper;
import com.br.pruma.core.domain.SubContrato;
import com.br.pruma.core.repository.SubContratoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubContratoService {

    private final SubContratoRepository repository;
    private final SubContratoMapper mapper;

    @Transactional(readOnly = true)
    public List<SubContratoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SubContratoResponseDTO> listByClienteCpf(String clienteCpf) {
        return repository.findByClienteCpf(clienteCpf).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SubContratoResponseDTO> listByProjetoId(Integer projetoId) {
        return repository.findByProjetoId(projetoId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SubContratoResponseDTO> listByClienteCpfAndProjetoId(String clienteCpf, Integer projetoId) {
        return repository.findByClienteCpfAndProjetoId(clienteCpf, projetoId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public SubContratoResponseDTO getById(Integer id) {
        SubContrato entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subcontrato não encontrado com id " + id));
        return mapper.toDTO(entity);
    }

    @Transactional
    public SubContratoResponseDTO create(SubContratoRequestDTO request) {
        SubContrato entity = mapper.toEntity(request);
        SubContrato saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Transactional
    public SubContratoResponseDTO update(Integer id, SubContratoRequestDTO request) {
        SubContrato existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subcontrato não encontrado com id " + id));
        mapper.updateFromDto(request, existing);
        SubContrato saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subcontrato não encontrado com id " + id);
        }
        repository.deleteById(id);
    }
}