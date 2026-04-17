package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.StatusSolicitacaoRequestDTO;
import com.br.pruma.application.dto.response.StatusSolicitacaoResponseDTO;
import com.br.pruma.application.dto.update.StatusSolicitacaoUpdateDTO;
import com.br.pruma.application.mapper.StatusSolicitacaoMapper;
import com.br.pruma.application.service.StatusSolicitacaoService;
import com.br.pruma.core.domain.StatusSolicitacao;
import com.br.pruma.core.repository.StatusSolicitacaoRepository;
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
public class StatusSolicitacaoServiceImpl implements StatusSolicitacaoService {

    private final StatusSolicitacaoRepository repository;
    private final StatusSolicitacaoMapper mapper;

    @Override
    public StatusSolicitacaoResponseDTO create(StatusSolicitacaoRequestDTO dto) {
        StatusSolicitacao entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public StatusSolicitacaoResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "StatusSolicitacao não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatusSolicitacaoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatusSolicitacaoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public StatusSolicitacaoResponseDTO update(Integer id, StatusSolicitacaoUpdateDTO dto) {
        StatusSolicitacao entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "StatusSolicitacao não encontrado: " + id));

        mapper.updateFromDto(dto, entity);

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public StatusSolicitacaoResponseDTO replace(Integer id, StatusSolicitacaoRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "StatusSolicitacao não encontrado: " + id));

        StatusSolicitacao entity = mapper.toEntity(dto);
        entity.setId(id);

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        StatusSolicitacao entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "StatusSolicitacao não encontrado: " + id));
        repository.delete(entity);
    }
}