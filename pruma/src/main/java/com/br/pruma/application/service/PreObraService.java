package com.br.pruma.application.service;


import com.br.pruma.application.dto.request.PreObraRequestDTO;
import com.br.pruma.application.dto.response.PreObraResponseDTO;
import com.br.pruma.application.dto.update.PreObraUpdateDTO;
import com.br.pruma.application.mapper.PreObraMapper;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.PreObra;

import com.br.pruma.core.repository.ObraRepository;
import com.br.pruma.core.repository.PreObraRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PreObraService {

    private final PreObraRepository preObraRepository;
    private final ObraRepository obraRepository;
    private final PreObraMapper mapper;

    /**
     * Cria um novo registro de pré-obra.
     */
    public PreObraResponseDTO create(PreObraRequestDTO dto) {
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));

        PreObra entity = mapper.toEntity(dto);
        entity.setObra(obra);

        PreObra saved = preObraRepository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Recupera um registro por id.
     */
    @Transactional(readOnly = true)
    public PreObraResponseDTO getById(Integer id) {
        PreObra entity = preObraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PreObra não encontrada: " + id));
        return mapper.toResponse(entity);
    }

    /**
     * Lista todos os registros (sem paginação).
     */
    @Transactional(readOnly = true)
    public List<PreObraResponseDTO> listAll() {
        return preObraRepository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista com paginação.
     */
    @Transactional(readOnly = true)
    public Page<PreObraResponseDTO> list(Pageable pageable) {
        return preObraRepository.findAll(pageable).map(mapper::toResponse);
    }

    /**
     * Lista registros por obra.
     */
    @Transactional(readOnly = true)
    public List<PreObraResponseDTO> listByObra(Integer obraId) {
        return preObraRepository.findAllByObra_Id(obraId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza parcialmente um registro existente.
     */
    public PreObraResponseDTO update(Integer id, PreObraUpdateDTO dto) {
        PreObra entity = preObraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PreObra não encontrada: " + id));

        if (dto.getObraId() != null) {
            Obra obra = obraRepository.findById(dto.getObraId())
                    .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
            entity.setObra(obra);
        }

        mapper.updateFromDto(dto, entity);
        PreObra updated = preObraRepository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Substituição completa dos campos permitidos (PUT semantics).
     */
    public PreObraResponseDTO replace(Integer id, PreObraRequestDTO dto) {
        PreObra existing = preObraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PreObra não encontrada: " + id));

        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));

        PreObra updated = mapper.toEntity(dto);
        updated.setId(existing.getId());
        updated.setObra(obra);

        PreObra saved = preObraRepository.save(updated);
        return mapper.toResponse(saved);
    }

    /**
     * Exclui permanentemente o registro.
     * Para soft-delete, adapte para marcar flag em vez de deletar.
     */
    public void delete(Integer id) {
        if (!preObraRepository.existsById(id)) {
            throw new EntityNotFoundException("PreObra não encontrada: " + id);
        }
        preObraRepository.deleteById(id);
    }

    /**
     * Busca por descrição (case-insensitive) com paginação.
     * Implementado via repository simples; retorna Page construído a partir de lista.
     */
    @Transactional(readOnly = true)
    public Page<PreObraResponseDTO> searchByDescricao(String descricao, Pageable pageable) {
        List<PreObra> found = preObraRepository.findAllByDescricaoContainingIgnoreCase(descricao);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), found.size());
        List<PreObraResponseDTO> content = found.subList(start, end).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, found.size());
    }
}