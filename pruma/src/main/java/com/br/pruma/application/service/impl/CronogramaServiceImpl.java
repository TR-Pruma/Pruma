package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.CronogramaRequestDTO;
import com.br.pruma.application.dto.response.CronogramaResponseDTO;
import com.br.pruma.application.dto.update.CronogramaUpdateDTO;
import com.br.pruma.application.mapper.CronogramaMapper;
import com.br.pruma.application.service.CronogramaService;
import com.br.pruma.core.domain.Cronograma;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.CronogramaRepository;
import com.br.pruma.core.repository.ProjetoRepository;
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
public class CronogramaServiceImpl implements CronogramaService {

    private final CronogramaRepository repository;
    private final ProjetoRepository projetoRepository;
    private final CronogramaMapper mapper;

    @Override
    public CronogramaResponseDTO create(CronogramaRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.projetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.projetoId()));
        Cronograma entity = mapper.toEntity(dto);
        entity.setProjeto(projeto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public CronogramaResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cronograma não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CronogramaResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CronogramaResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CronogramaResponseDTO> listByProjeto(Integer projetoId) {
        return repository.findAllByProjetoId(projetoId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public CronogramaResponseDTO update(Integer id, CronogramaUpdateDTO dto) {
        Cronograma entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cronograma não encontrado: " + id));
        if (dto.getProjetoId() != null) {
            Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                    .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
            entity.setProjeto(projeto);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public CronogramaResponseDTO replace(Integer id, CronogramaRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cronograma não encontrado: " + id));
        Projeto projeto = projetoRepository.findById(dto.projetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.projetoId()));
        Cronograma entity = mapper.toEntity(dto);
        entity.setId(id);
        entity.setProjeto(projeto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        Cronograma entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cronograma não encontrado: " + id));
        repository.delete(entity);
    }
}
