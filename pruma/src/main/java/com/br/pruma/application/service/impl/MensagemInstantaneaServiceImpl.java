package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.MensagemInstantaneaRequestDTO;
import com.br.pruma.application.dto.response.MensagemInstantaneaResponseDTO;
import com.br.pruma.application.dto.update.MensagemInstantaneaUpdateDTO;
import com.br.pruma.application.service.MensagemInstantaneaService;
import com.br.pruma.core.domain.MensagemInstantanea;
import com.br.pruma.core.exception.NotFoundException;
import com.br.pruma.core.mapper.MensagemInstantaneaMapper;
import com.br.pruma.core.port.out.MensagemInstantaneaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MensagemInstantaneaServiceImpl implements MensagemInstantaneaService {

    private final MensagemInstantaneaRepositoryPort repositoryPort;
    private final MensagemInstantaneaMapper mapper;

    @Override
    @Transactional
    public MensagemInstantaneaResponseDTO create(MensagemInstantaneaRequestDTO dto) {
        MensagemInstantanea entity = mapper.toEntity(dto);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    public MensagemInstantaneaResponseDTO getById(Integer id) {
        return mapper.toResponse(findOrThrow(id));
    }

    @Override
    public List<MensagemInstantaneaResponseDTO> listAll() {
        return repositoryPort.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public Page<MensagemInstantaneaResponseDTO> list(Pageable pageable) {
        return repositoryPort.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public MensagemInstantaneaResponseDTO update(Integer id, MensagemInstantaneaUpdateDTO dto) {
        MensagemInstantanea entity = findOrThrow(id);
        mapper.updateEntity(dto, entity);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        findOrThrow(id);
        repositoryPort.deleteById(id);
    }

    private MensagemInstantanea findOrThrow(Integer id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("MensagemInstantanea não encontrada: " + id));
    }
}
