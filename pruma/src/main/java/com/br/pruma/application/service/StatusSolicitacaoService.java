package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.StatusSolicitacaoRequestDTO;
import com.br.pruma.application.dto.response.StatusSolicitacaoResponseDTO;
import com.br.pruma.application.dto.update.StatusSolicitacaoUpdateDTO;
import com.br.pruma.application.mapper.StatusSolicitacaoMapper;
import com.br.pruma.core.domain.StatusSolicitacao;
import com.br.pruma.core.repository.StatusSolicitacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public StatusSolicitacaoResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status de Solicitação não encontrado: " + id)));
    }

    @Transactional(readOnly = true)
    public List<StatusSolicitacaoResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<StatusSolicitacaoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public StatusSolicitacaoResponseDTO update(Integer id, StatusSolicitacaoUpdateDTO dto) {
        StatusSolicitacao entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status de Solicitação não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public StatusSolicitacaoResponseDTO replace(Integer id, StatusSolicitacaoRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Status de Solicitação não encontrado: " + id));
        StatusSolicitacao entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Status de Solicitação não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
