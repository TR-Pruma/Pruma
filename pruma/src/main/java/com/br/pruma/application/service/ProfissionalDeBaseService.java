package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ProfissionalDeBaseRequestDTO;
import com.br.pruma.application.dto.response.ProfissionalDeBaseResponseDTO;
import com.br.pruma.application.dto.update.ProfissionalDeBaseUpdateDTO;
import com.br.pruma.application.mapper.ProfissionalDeBaseMapper;
import com.br.pruma.core.domain.ProfissionalDeBase;
import com.br.pruma.core.repository.ProfissionalDeBaseRepository;
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
public class ProfissionalDeBaseService {

    private final ProfissionalDeBaseRepository repository;
    private final ProfissionalDeBaseMapper mapper;

    /**
     * Cria um novo profissional.
     */
    public ProfissionalDeBaseResponseDTO create(ProfissionalDeBaseRequestDTO dto) {
        ProfissionalDeBase entity = mapper.toEntity(dto);
        ProfissionalDeBase saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * Recupera por id.
     */
    @Transactional(readOnly = true)
    public ProfissionalDeBaseResponseDTO getById(Integer id) {
        ProfissionalDeBase entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado: " + id));
        return mapper.toResponse(entity);
    }

    /**
     * Lista todos (sem paginação).
     */
    @Transactional(readOnly = true)
    public List<ProfissionalDeBaseResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista com paginação.
     */
    @Transactional(readOnly = true)
    public Page<ProfissionalDeBaseResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    /**
     * Lista por nome (contendo, case-insensitive).
     */
    @Transactional(readOnly = true)
    public List<ProfissionalDeBaseResponseDTO> listByNome(String nome) {
        return repository.findAllByNomeContainingIgnoreCase(nome).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lista por especialidade (exata).
     */
    @Transactional(readOnly = true)
    public List<ProfissionalDeBaseResponseDTO> listByEspecialidade(String especialidade) {
        return repository.findAllByEspecialidade(especialidade).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza parcialmente (aplica somente campos não nulos do DTO).
     */
    public ProfissionalDeBaseResponseDTO update(Integer id, ProfissionalDeBaseUpdateDTO dto) {
        ProfissionalDeBase entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado: " + id));

        mapper.updateFromDto(dto, entity);
        ProfissionalDeBase updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Substituição completa de campos permitidos (PUT semantics).
     */
    public ProfissionalDeBaseResponseDTO replace(Integer id, ProfissionalDeBaseRequestDTO dto) {
        ProfissionalDeBase existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Profissional não encontrado: " + id));

        ProfissionalDeBase updated = mapper.toEntity(dto);
        updated.setId(existing.getId());
        ProfissionalDeBase saved = repository.save(updated);
        return mapper.toResponse(saved);
    }

    /**
     * Exclui permanentemente um profissional.
     */
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Profissional não encontrado: " + id);
        }
        repository.deleteById(id);
    }

    /**
     * Busca por nome com paginação (retorna Page construído a partir do resultado do repository).
     */
    @Transactional(readOnly = true)
    public Page<ProfissionalDeBaseResponseDTO> searchByNome(String nome, Pageable pageable) {
        List<ProfissionalDeBase> found = repository.findAllByNomeContainingIgnoreCase(nome);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), found.size());
        List<ProfissionalDeBaseResponseDTO> content = found.subList(start, end).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(content, pageable, found.size());
    }
}
