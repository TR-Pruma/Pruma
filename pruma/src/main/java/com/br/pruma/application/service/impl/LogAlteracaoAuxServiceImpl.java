package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.LogAlteracaoAuxRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoAuxResponseDTO;
import com.br.pruma.application.dto.update.LogAlteracaoAuxUpdateDTO;
import com.br.pruma.application.service.LogAlteracaoAuxService;
import com.br.pruma.core.domain.LogAlteracaoAux;
import com.br.pruma.core.exception.NotFoundException;
import com.br.pruma.core.mapper.LogAlteracaoAuxMapper;
import com.br.pruma.core.port.out.LogAlteracaoAuxRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogAlteracaoAuxServiceImpl implements LogAlteracaoAuxService {

    private final LogAlteracaoAuxRepositoryPort repositoryPort;
    private final LogAlteracaoAuxMapper mapper;

    @Override
    @Transactional
    public LogAlteracaoAuxResponseDTO create(LogAlteracaoAuxRequestDTO dto) {
        LogAlteracaoAux entity = mapper.toEntity(dto);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    public LogAlteracaoAuxResponseDTO getById(Integer id) {
        return mapper.toResponse(findOrThrow(id));
    }

    @Override
    public List<LogAlteracaoAuxResponseDTO> listAll() {
        return repositoryPort.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public Page<LogAlteracaoAuxResponseDTO> list(Pageable pageable) {
        return repositoryPort.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public LogAlteracaoAuxResponseDTO update(Integer id, LogAlteracaoAuxUpdateDTO dto) {
        LogAlteracaoAux entity = findOrThrow(id);
        mapper.updateEntity(dto, entity);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        findOrThrow(id);
        repositoryPort.deleteById(id);
    }

    private LogAlteracaoAux findOrThrow(Integer id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("LogAlteracaoAux não encontrado: " + id));
    }
}
