package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.MensagemInstantaneaAuxRequestDTO;
import com.br.pruma.application.dto.response.MensagemInstantaneaAuxResponseDTO;
import com.br.pruma.application.dto.update.MensagemInstantaneaAuxUpdateDTO;
import com.br.pruma.application.mapper.MensagemInstantaneaAuxMapper;
import com.br.pruma.application.service.MensagemInstantaneaAuxService;
import com.br.pruma.core.domain.MensagemInstantaneaAux;
import com.br.pruma.core.exception.RecursoNaoEncontradoException;
import com.br.pruma.core.repository.port.MensagemInstantaneaAuxRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MensagemInstantaneaAuxServiceImpl implements MensagemInstantaneaAuxService {

    private final MensagemInstantaneaAuxRepositoryPort repositoryPort;
    private final MensagemInstantaneaAuxMapper mapper;

    @Override
    @Transactional
    public MensagemInstantaneaAuxResponseDTO create(MensagemInstantaneaAuxRequestDTO dto) {
        MensagemInstantaneaAux entity = mapper.toEntity(dto);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    public MensagemInstantaneaAuxResponseDTO getById(Integer id) {
        return mapper.toResponse(findOrThrow(id));
    }

    @Override
    public List<MensagemInstantaneaAuxResponseDTO> listAll() {
        return repositoryPort.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public Page<MensagemInstantaneaAuxResponseDTO> list(Pageable pageable) {
        return repositoryPort.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public MensagemInstantaneaAuxResponseDTO update(Integer id, MensagemInstantaneaAuxUpdateDTO dto) {
        MensagemInstantaneaAux entity = findOrThrow(id);
        mapper.updateEntity(dto, entity);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        findOrThrow(id);
        repositoryPort.deleteById(id);
    }

    private MensagemInstantaneaAux findOrThrow(Integer id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("MensagemInstantaneaAux não encontrada: " + id));
    }
}
