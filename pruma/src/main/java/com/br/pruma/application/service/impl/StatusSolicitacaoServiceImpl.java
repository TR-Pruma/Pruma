package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.StatusSolicitacaoRequestDTO;
import com.br.pruma.application.dto.response.StatusSolicitacaoResponseDTO;
import com.br.pruma.application.dto.update.StatusSolicitacaoUpdateDTO;
import com.br.pruma.application.mapper.StatusSolicitacaoMapper;
import com.br.pruma.application.service.StatusSolicitacaoService;
import com.br.pruma.config.Constantes;
import com.br.pruma.core.domain.StatusSolicitacao;
import com.br.pruma.core.repository.port.StatusSolicitacaoRepositoryPort;
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

    private final StatusSolicitacaoRepositoryPort statusSolicitacaoRepositoryPort;
    private final StatusSolicitacaoMapper statusSolicitacaoMapper;

    @Override
    public StatusSolicitacaoResponseDTO create(StatusSolicitacaoRequestDTO dto) {
        StatusSolicitacao statusSolicitacao = statusSolicitacaoMapper.toEntity(dto);
        StatusSolicitacao saved = statusSolicitacaoRepositoryPort.save(statusSolicitacao);
        return statusSolicitacaoMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public StatusSolicitacaoResponseDTO getById(Integer id) {
        StatusSolicitacao statusSolicitacao = statusSolicitacaoRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.STATUS_SOLICITACAO_NAO_ENCONTRADO + id));
        return statusSolicitacaoMapper.toResponse(statusSolicitacao);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatusSolicitacaoResponseDTO> listAll() {
        return statusSolicitacaoRepositoryPort.findAll()
                .stream()
                .map(statusSolicitacaoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatusSolicitacaoResponseDTO> list(Pageable pageable) {
        return statusSolicitacaoRepositoryPort.findAll(pageable)
                .map(statusSolicitacaoMapper::toResponse);
    }

    @Override
    public StatusSolicitacaoResponseDTO update(Integer id, StatusSolicitacaoUpdateDTO dto) {
        StatusSolicitacao statusSolicitacao = statusSolicitacaoRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.STATUS_SOLICITACAO_NAO_ENCONTRADO + id));
        statusSolicitacaoMapper.updateFromDto(dto, statusSolicitacao);
        StatusSolicitacao updated = statusSolicitacaoRepositoryPort.save(statusSolicitacao);
        return statusSolicitacaoMapper.toResponse(updated);
    }

    @Override
    public StatusSolicitacaoResponseDTO replace(Integer id, StatusSolicitacaoRequestDTO dto) {
        statusSolicitacaoRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.STATUS_SOLICITACAO_NAO_ENCONTRADO + id));
        StatusSolicitacao statusSolicitacao = statusSolicitacaoMapper.toEntity(dto);
        statusSolicitacao.setId(id);
        StatusSolicitacao replaced = statusSolicitacaoRepositoryPort.save(statusSolicitacao);
        return statusSolicitacaoMapper.toResponse(replaced);
    }

    @Override
    public void delete(Integer id) {
        StatusSolicitacao statusSolicitacao = statusSolicitacaoRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.STATUS_SOLICITACAO_NAO_ENCONTRADO + id));
        statusSolicitacaoRepositoryPort.delete(statusSolicitacao);
    }
}
