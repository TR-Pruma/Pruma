package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ChecklistRequestDTO;
import com.br.pruma.application.dto.response.ChecklistResponseDTO;
import com.br.pruma.application.mapper.ChecklistMapper;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Checklist;
import com.br.pruma.core.repository.ChecklistRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChecklistService {

    private final ChecklistRepository checklistRepository;
    private final ProjetoRepository projetoRepository;
    private final ChecklistMapper checklistMapper;
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<ChecklistResponseDTO> findByProjeto(Integer projetoId) {
        if (!projetoRepository.existsById(projetoId)) {
            throw new RecursoNaoEncontradoException("Projeto não encontrado");
        }
        return checklistRepository.findByProjetoIdWithItens(projetoId).stream()
                .map(checklistMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ChecklistResponseDTO findById(Integer id) {
        return checklistRepository.findByIdWithItens(id)
                .map(checklistMapper::toResponseDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Checklist não encontrado"));
    }

    @Transactional
    public ChecklistResponseDTO create(@Valid ChecklistRequestDTO dto) {
        validarProjeto(dto.getProjetoId());
        validarNomeUnico(dto.getNome(), dto.getProjetoId(), null);

        Checklist checklist = checklistMapper.toEntity(dto);
        checklist = checklistRepository.save(checklist);

        // Ativa o filtro de registros ativos
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("ativoFilter").setParameter("ativo", true);

        return checklistMapper.toResponseDTO(checklist);
    }

    @Transactional
    public ChecklistResponseDTO update(Integer id, @Valid ChecklistRequestDTO dto) {
        Checklist checklist = checklistRepository.findByIdWithItens(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Checklist não encontrado"));

        validarProjeto(dto.getProjetoId());
        validarNomeUnico(dto.getNome(), dto.getProjetoId(), id);

        if (!checklist.getProjeto().getId().equals(dto.getProjetoId())) {
            throw new IllegalArgumentException("Não é permitido alterar o projeto do checklist");
        }

        checklistMapper.updateEntity(checklist, dto);
        checklist = checklistRepository.save(checklist);

        return checklistMapper.toResponseDTO(checklist);
    }

    @Transactional
    public void delete(Integer id) {
        Checklist checklist = checklistRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Checklist não encontrado"));

        checklistRepository.softDelete(id);
    }

    private void validarProjeto(Integer projetoId) {
        if (!projetoRepository.existsById(projetoId)) {
            throw new RecursoNaoEncontradoException("Projeto não encontrado");
        }
    }

    private void validarNomeUnico(String nome, Integer projetoId, Integer checklistId) {
        if (checklistId == null) {
            if (checklistRepository.existsByNomeAndProjetoIdAndAtivoTrue(nome, projetoId)) {
                throw new IllegalArgumentException("Já existe um checklist com este nome no projeto");
            }
        } else {
            if (checklistRepository.existsByNomeAndProjetoIdAndIdNot(nome, projetoId, checklistId)) {
                throw new IllegalArgumentException("Já existe um checklist com este nome no projeto");
            }
        }
    }
}
