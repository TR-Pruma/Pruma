package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ProjetoRequestDTO;
import com.br.pruma.application.dto.response.ProjetoResponseDTO;
import com.br.pruma.application.dto.update.ProjetoUpdateDTO;
import com.br.pruma.application.mapper.ProjetoMapper;
import com.br.pruma.core.domain.Projeto;

import com.br.pruma.core.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço de domínio para Projeto.
 * Responsabilidades: CRUD, listagens simples/paginadas e buscas por nome/data.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProjetoService {

    private final ProjetoRepository repository;
    private final ProjetoMapper mapper;

    /**
     * Cria um novo projeto.
     */
    public ProjetoResponseDTO create(ProjetoRequestDTO dto) {
        Projeto entity = mapper.toEntity(dto);
        Projeto saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Recupera projeto por id.
     */
    @Transactional(readOnly = true)
    public ProjetoResponseDTO getById(Integer id) {
        Projeto entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + id));
        return mapper.toResponse(entity);
    }

    /**
     * Lista todos os projetos (sem paginação).
     */
    @Transactional(readOnly = true)
    public List<ProjetoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista com paginação.
     */
    @Transactional(readOnly = true)
    public Page<ProjetoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    /**
     * Lista projetos cujo nome contém o texto (case-insensitive).
     */
    @Transactional(readOnly = true)
    public List<ProjetoResponseDTO> listByNome(String nome) {
        return repository.findAllByNomeContainingIgnoreCase(nome).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza parcialmente um projeto existente (aplica somente campos não-nulos).
     */
    public ProjetoResponseDTO update(Integer id, ProjetoUpdateDTO dto) {
        Projeto entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + id));

        mapper.updateFromDto(dto, entity);
        Projeto updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Substituição completa dos campos permitidos (PUT semantics).
     */
    public ProjetoResponseDTO replace(Integer id, ProjetoRequestDTO dto) {
        Projeto existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + id));

        Projeto updated = mapper.toEntity(dto);
        updated.setId(existing.getId());
        Projeto saved = repository.save(updated);
        return mapper.toResponse(saved);
    }

    /**
     * Exclui permanentemente um projeto.
     */
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Projeto não encontrado: " + id);
        }
        repository.deleteById(id);
    }

    /**
     * Busca por nome com paginação: constrói Page a partir de lista do repository.
     */
    @Transactional(readOnly = true)
    public Page<ProjetoResponseDTO> searchByNome(String nome, Pageable pageable) {
        List<Projeto> found = repository.findAllByNomeContainingIgnoreCase(nome);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), found.size());
        List<ProjetoResponseDTO> content = found.subList(start, end).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, found.size());
    }
}