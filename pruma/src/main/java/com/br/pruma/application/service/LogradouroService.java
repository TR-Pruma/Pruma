package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.LogradouroRequestDTO;
import com.br.pruma.application.dto.response.LogradouroResponseDTO;
import com.br.pruma.application.dto.update.LogradouroUpdateDTO;
import com.br.pruma.application.mapper.LogradouroMapper;
import com.br.pruma.core.domain.Logradouro;
import com.br.pruma.core.repository.LogradouroRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LogradouroService {

    private final LogradouroRepository repository;
    private final LogradouroMapper mapper;

    /**
     * Cria um novo logradouro.
     */
    public LogradouroResponseDTO create(LogradouroRequestDTO dto) {
        // evita duplicidade de tipo
        repository.findByTipo(dto.getTipo())
                .ifPresent(l -> {
                    throw new IllegalArgumentException("Logradouro já cadastrado: " + dto.getTipo());
                });

        Logradouro entity = mapper.toEntity(dto);
        Logradouro saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Recupera um logradouro por ID.
     */
    @Transactional(readOnly = true)
    public LogradouroResponseDTO getById(Integer id) {
        Logradouro entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Logradouro não encontrado: " + id)
                );
        return mapper.toResponse(entity);
    }

    /**
     * Lista todos os logradouros ordenados pelo tipo.
     */
    @Transactional(readOnly = true)
    public List<LogradouroResponseDTO> listAll() {
        return repository.findAllByOrderByTipoAsc().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza parcialmente um logradouro existente.
     */
    public LogradouroResponseDTO update(Integer id, LogradouroUpdateDTO dto) {
        Logradouro entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Logradouro não encontrado: " + id)
                );

        // se dto.tipo não for nulo, verifica duplicidade
        if (dto.getTipo() != null) {
            repository.findByTipo(dto.getTipo())
                    .filter(l -> !l.getId().equals(id))
                    .ifPresent(l -> {
                        throw new IllegalArgumentException("Outro logradouro já usa este tipo: " + dto.getTipo());
                    });
        }

        mapper.updateFromDto(dto, entity);
        Logradouro updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Remove um logradouro por ID.
     */
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Logradouro não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}