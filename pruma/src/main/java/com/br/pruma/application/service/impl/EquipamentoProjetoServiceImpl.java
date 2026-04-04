package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.EquipamentoProjetoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoProjetoResponseDTO;
import com.br.pruma.application.dto.update.EquipamentoProjetoUpdateDTO;
import com.br.pruma.application.mapper.EquipamentoProjetoMapper;
import com.br.pruma.application.service.EquipamentoProjetoService;
import com.br.pruma.core.domain.EquipamentoProjeto;
import com.br.pruma.core.repository.EquipamentoProjetoRepository;
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
public class EquipamentoProjetoServiceImpl implements EquipamentoProjetoService {

    private final EquipamentoProjetoRepository repository;
    private final EquipamentoProjetoMapper mapper;

    @Override
    public EquipamentoProjetoResponseDTO create(EquipamentoProjetoRequestDTO dto) {
        EquipamentoProjeto entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public EquipamentoProjetoResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EquipamentoProjeto não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipamentoProjetoResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipamentoProjetoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipamentoProjetoResponseDTO> listByProjeto(Integer projetoId) {
        return repository.findAllByProjeto_Id(projetoId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public EquipamentoProjetoResponseDTO update(Integer id, EquipamentoProjetoUpdateDTO dto) {
        EquipamentoProjeto entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EquipamentoProjeto não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("EquipamentoProjeto não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
