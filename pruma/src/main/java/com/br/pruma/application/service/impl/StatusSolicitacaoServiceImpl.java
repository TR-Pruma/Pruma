package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.StatusSolicitacaoRequestDTO;
import com.br.pruma.application.dto.response.StatusSolicitacaoResponseDTO;
import com.br.pruma.application.service.StatusSolicitacaoService;
import com.br.pruma.core.domain.StatusSolicitacao;
import com.br.pruma.core.repository.StatusSolicitacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusSolicitacaoServiceImpl implements StatusSolicitacaoService {

    private final StatusSolicitacaoRepository statusSolicitacaoRepository;

    @Override
    @Transactional
    public StatusSolicitacaoResponseDTO criar(StatusSolicitacaoRequestDTO request) {
        StatusSolicitacao entity = StatusSolicitacao.builder()
                .descricao(request.descricao())
                .status(request.status())
                .build();
        return toResponse(statusSolicitacaoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatusSolicitacaoResponseDTO> listarTodos() {
        return statusSolicitacaoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StatusSolicitacaoResponseDTO buscarPorId(Integer id) {
        return statusSolicitacaoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("StatusSolicitacao não encontrado: " + id));
    }

    @Override
    @Transactional
    public StatusSolicitacaoResponseDTO atualizar(Integer id, StatusSolicitacaoRequestDTO request) {
        StatusSolicitacao entity = statusSolicitacaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StatusSolicitacao não encontrado: " + id));
        entity.setDescricao(request.descricao());
        entity.setStatus(request.status());
        return toResponse(statusSolicitacaoRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!statusSolicitacaoRepository.existsById(id)) {
            throw new EntityNotFoundException("StatusSolicitacao não encontrado: " + id);
        }
        statusSolicitacaoRepository.deleteById(id);
    }

    private StatusSolicitacaoResponseDTO toResponse(StatusSolicitacao e) {
        return new StatusSolicitacaoResponseDTO(
                e.getId(),
                e.getDescricao(),
                e.getStatus(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
