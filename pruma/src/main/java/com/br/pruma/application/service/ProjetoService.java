package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ProjetoRequestDTO;
import com.br.pruma.application.dto.response.ProjetoResponseDTO;
import com.br.pruma.application.dto.update.ProjetoUpdateDTO;
import com.br.pruma.application.mapper.ProjetoMapper;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjetoService {

    private final ProjetoRepository repository;
    private final ProjetoMapper mapper;

    public ProjetoResponseDTO create(ProjetoRequestDTO dto) {
        return mapper.toResponse(repository.save(mapper.toEntity(dto)));
    }

    @Transactional(readOnly = true)
    public ProjetoResponseDTO getById(Integer id) {
        return mapper.toResponse(
                repository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + id))
        );
    }

    @Transactional(readOnly = true)
    public List<ProjetoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ProjetoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<ProjetoResponseDTO> listByNome(String nome) {
        return repository.findAllByNomeContainingIgnoreCase(nome).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ProjetoResponseDTO> listByNomePaginado(String nome, Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ProjetoResponseDTO> listByDataCriacao(LocalDate date, Pageable pageable) {
        return repository.findAllByDataCriacao(date, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ProjetoResponseDTO> listByDataCriacaoBetween(LocalDate start, LocalDate end, Pageable pageable) {
        return repository.findAllByDataCriacaoBetween(start, end, pageable).map(mapper::toResponse);
    }

    public ProjetoResponseDTO update(Integer id, ProjetoUpdateDTO dto) {
        Projeto entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public ProjetoResponseDTO replace(Integer id, ProjetoRequestDTO dto) {
        Projeto existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + id));
        Projeto updated = mapper.toEntity(dto);
        updated.setId(existing.getId());
        return mapper.toResponse(repository.save(updated));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Projeto não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
