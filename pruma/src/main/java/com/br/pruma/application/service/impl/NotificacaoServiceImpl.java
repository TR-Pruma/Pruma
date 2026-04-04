package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.NotificacaoRequestDTO;
import com.br.pruma.application.dto.response.NotificacaoResponseDTO;
import com.br.pruma.application.dto.update.NotificacaoUpdateDTO;
import com.br.pruma.application.mapper.NotificacaoMapper;
import com.br.pruma.application.service.NotificacaoService;
import com.br.pruma.core.domain.Notificacao;
import com.br.pruma.core.repository.NotificacaoRepository;
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
public class NotificacaoServiceImpl implements NotificacaoService {

    private final NotificacaoRepository repository;
    private final NotificacaoMapper mapper;

    @Override
    public NotificacaoResponseDTO create(NotificacaoRequestDTO dto) {
        Notificacao entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public NotificacaoResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacaoResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificacaoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacaoResponseDTO> listByUsuario(Integer usuarioId) {
        return repository.findAllByUsuario_Id(usuarioId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public NotificacaoResponseDTO marcarComoLida(Integer id) {
        Notificacao entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada: " + id));
        entity.setLida(true);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public NotificacaoResponseDTO update(Integer id, NotificacaoUpdateDTO dto) {
        Notificacao entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notificação não encontrada: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Notificação não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
