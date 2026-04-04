package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ProjetoRequestDTO;
import com.br.pruma.application.dto.response.ProjetoResponseDTO;
import com.br.pruma.application.dto.update.ProjetoUpdateDTO;
import com.br.pruma.application.mapper.ProjetoMapper;
import com.br.pruma.application.service.ProjetoService;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ProjetoRepository;
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
public class ProjetoServiceImpl implements ProjetoService {

    private final ProjetoRepository repository;
    private final ProjetoMapper mapper;

    @Override
    public ProjetoResponseDTO create(ProjetoRequestDTO dto) {
        Projeto entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ProjetoResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjetoResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjetoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjetoResponseDTO> listByCliente(Integer clienteId) {
        return repository.findAllByCliente_Id(clienteId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public ProjetoResponseDTO update(Integer id, ProjetoUpdateDTO dto) {
        Projeto entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public ProjetoResponseDTO replace(Integer id, ProjetoRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + id));
        Projeto entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Projeto não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
