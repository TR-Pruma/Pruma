package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.MaterialRequestDTO;
import com.br.pruma.application.dto.response.MaterialResponseDTO;
import com.br.pruma.application.dto.update.MaterialUpdateDTO;
import com.br.pruma.application.mapper.MaterialMapper;
import com.br.pruma.core.domain.Material;
import com.br.pruma.core.repository.MaterialRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MaterialService {

    private final MaterialRepository repository;
    private final MaterialMapper mapper;

    /**
     * Cria um novo material validando unicidade da descrição.
     */
    public MaterialResponseDTO create(MaterialRequestDTO dto) {
        repository.findByDescricao(dto.getDescricao())
                .ifPresent(m -> {
                    throw new IllegalArgumentException(
                            "Já existe material com essa descrição: " + dto.getDescricao()
                    );
                });

        Material entity = mapper.toEntity(dto);
        Material saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Recupera um material por ID ou lança EntityNotFoundException.
     */
    @Transactional(readOnly = true)
    public MaterialResponseDTO getById(Integer id) {
        Material entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Material não encontrado: " + id)
                );
        return mapper.toResponse(entity);
    }

    /**
     * Lista todos os materiais ordenados pela descrição.
     */
    @Transactional(readOnly = true)
    public List<MaterialResponseDTO> listAll() {
        return repository.findAllByOrderByDescricaoAsc().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza parcialmente um material existente.
     * Se for alterada a descrição, valida duplicidade.
     */
    public MaterialResponseDTO update(Integer id, MaterialUpdateDTO dto) {
        Material entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Material não encontrado: " + id)
                );

        if (dto.getDescricao() != null) {
            repository.findByDescricao(dto.getDescricao())
                    .filter(m -> !m.getId().equals(id))
                    .ifPresent(m -> {
                        throw new IllegalArgumentException(
                                "Outro material já usa essa descrição: " + dto.getDescricao()
                        );
                    });
        }

        mapper.updateFromDto(dto, entity);
        Material updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Exclui um material por ID.
     */
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Material não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}