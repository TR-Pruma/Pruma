package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.StatusSolicitacaoRequestDTO;
import com.br.pruma.application.dto.response.StatusSolicitacaoResponseDTO;
import com.br.pruma.application.dto.update.StatusSolicitacaoUpdateDTO;
import com.br.pruma.application.mapper.StatusSolicitacaoMapper;
import com.br.pruma.core.domain.StatusSolicitacao;
import com.br.pruma.core.repository.StatusSolicitacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StatusSolicitacaoService {

    private final StatusSolicitacaoRepository repository;
    private final StatusSolicitacaoMapper mapper;

    public StatusSolicitacaoResponseDTO create(StatusSolicitacaoRequestDTO dto) {
        StatusSolicitacao entity = mapper.toEntity(dto);
        StatusSolicitacao saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public StatusSolicitacaoResponseDTO getById(Integer id) {
        StatusSolicitacao entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status de solicitação não encontrado: " + id));
        return mapper.toResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<StatusSolicitacaoResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public StatusSolicitacaoResponseDTO update(Integer id, StatusSolicitacaoUpdateDTO dto) {
        StatusSolicitacao entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status de solicitação não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        StatusSolicitacao updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Status de solicitação não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
