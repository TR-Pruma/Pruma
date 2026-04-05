package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.LogradouroRequestDTO;
import com.br.pruma.application.dto.response.LogradouroResponseDTO;
import com.br.pruma.application.dto.update.LogradouroUpdateDTO;
import com.br.pruma.application.service.LogradouroService;
import com.br.pruma.core.domain.Logradouro;
import com.br.pruma.core.exception.NotFoundException;
import com.br.pruma.core.mapper.LogradouroMapper;
import com.br.pruma.core.port.out.LogradouroRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogradouroServiceImpl implements LogradouroService {

    private final LogradouroRepositoryPort repositoryPort;
    private final LogradouroMapper mapper;

    @Override
    @Transactional
    public LogradouroResponseDTO create(LogradouroRequestDTO dto) {
        Logradouro entity = mapper.toEntity(dto);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    public LogradouroResponseDTO getById(Integer id) {
        return mapper.toResponse(findOrThrow(id));
    }

    @Override
    public List<LogradouroResponseDTO> listAll() {
        return repositoryPort.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public Page<LogradouroResponseDTO> list(Pageable pageable) {
        return repositoryPort.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public LogradouroResponseDTO update(Integer id, LogradouroUpdateDTO dto) {
        Logradouro entity = findOrThrow(id);
        mapper.updateEntity(dto, entity);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        findOrThrow(id);
        repositoryPort.deleteById(id);
    }

    private Logradouro findOrThrow(Integer id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Logradouro não encontrado: " + id));
    }
}
