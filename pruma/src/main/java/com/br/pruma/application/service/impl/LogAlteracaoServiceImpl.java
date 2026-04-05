package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.LogAlteracaoRequestDTO;
import com.br.pruma.application.dto.response.LogAlteracaoResponseDTO;
import com.br.pruma.application.dto.update.LogAlteracaoUpdateDTO;
import com.br.pruma.application.service.LogAlteracaoService;
import com.br.pruma.core.domain.LogAlteracao;
import com.br.pruma.core.exception.NotFoundException;
import com.br.pruma.core.mapper.LogAlteracaoMapper;
import com.br.pruma.core.port.out.LogAlteracaoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogAlteracaoServiceImpl implements LogAlteracaoService {

    private final LogAlteracaoRepositoryPort repositoryPort;
    private final LogAlteracaoMapper mapper;

    @Override
    @Transactional
    public LogAlteracaoResponseDTO create(LogAlteracaoRequestDTO dto) {
        LogAlteracao entity = mapper.toEntity(dto);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    public LogAlteracaoResponseDTO getById(Integer id) {
        return mapper.toResponse(findOrThrow(id));
    }

    @Override
    public List<LogAlteracaoResponseDTO> listAll() {
        return repositoryPort.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public Page<LogAlteracaoResponseDTO> list(Pageable pageable) {
        return repositoryPort.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional
    public LogAlteracaoResponseDTO update(Integer id, LogAlteracaoUpdateDTO dto) {
        LogAlteracao entity = findOrThrow(id);
        mapper.updateEntity(dto, entity);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        findOrThrow(id);
        repositoryPort.deleteById(id);
    }

    private LogAlteracao findOrThrow(Integer id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("LogAlteracao não encontrado: " + id));
    }
}
