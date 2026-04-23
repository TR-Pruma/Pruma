package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ChecklistRequestDTO;
import com.br.pruma.application.dto.response.ChecklistResponseDTO;
import com.br.pruma.application.dto.update.ChecklistUpdateDTO;
import com.br.pruma.application.mapper.ChecklistMapper;
import com.br.pruma.application.service.ChecklistService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Checklist;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ChecklistRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChecklistServiceImpl implements ChecklistService {

    private final ChecklistRepository repository;
    private final ProjetoRepository projetoRepository;
    private final ChecklistMapper mapper;

    @Override
    public ChecklistResponseDTO create(ChecklistRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Projeto com ID " + dto.getProjetoId() + " não encontrado."));
        Checklist entity = mapper.toEntity(dto);
        entity.setProjeto(projeto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ChecklistResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Checklist com ID " + id + " não encontrado.")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChecklistResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ChecklistResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChecklistResponseDTO> listByProjeto(Integer projetoId) {
        return repository.findByProjetoIdWithItens(projetoId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ChecklistResponseDTO update(Integer id, ChecklistUpdateDTO dto) {
        Checklist entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Checklist com ID " + id + " não encontrado."));
        if (dto.getProjetoId() != null) {
            Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Projeto com ID " + dto.getProjetoId() + " não encontrado."));
            entity.setProjeto(projeto);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public ChecklistResponseDTO replace(Integer id, ChecklistRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Checklist com ID " + id + " não encontrado."));
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Projeto com ID " + dto.getProjetoId() + " não encontrado."));
        Checklist entity = mapper.toEntity(dto);
        entity.setId(id);
        entity.setProjeto(projeto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        Checklist entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Checklist com ID " + id + " não encontrado."));
        entity.setAtivo(false);
        repository.save(entity);
    }
}
