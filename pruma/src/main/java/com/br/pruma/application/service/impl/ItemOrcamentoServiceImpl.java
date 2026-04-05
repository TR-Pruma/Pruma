package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ItemOrcamentoRequestDTO;
import com.br.pruma.application.dto.response.ItemOrcamentoResponseDTO;
import com.br.pruma.application.dto.update.ItemOrcamentoUpdateDTO;
import com.br.pruma.application.mapper.ItemOrcamentoMapper;
import com.br.pruma.application.service.ItemOrcamentoService;
import com.br.pruma.core.domain.ItemOrcamento;
import com.br.pruma.core.repository.port.ItemOrcamentoRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemOrcamentoServiceImpl implements ItemOrcamentoService {

    private final ItemOrcamentoRepositoryPort repositoryPort;
    private final ItemOrcamentoMapper mapper;

    @Override
    public ItemOrcamentoResponseDTO create(ItemOrcamentoRequestDTO dto) {
        ItemOrcamento entity = mapper.toEntity(dto);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ItemOrcamentoResponseDTO getById(Integer id) {
        return mapper.toResponse(repositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemOrcamento não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemOrcamentoResponseDTO> listAll() {
        return repositoryPort.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemOrcamentoResponseDTO> list(Pageable pageable) {
        List<ItemOrcamentoResponseDTO> all = repositoryPort.findAll().stream()
                .map(mapper::toResponse)
                .toList();
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), all.size());
        List<ItemOrcamentoResponseDTO> page = (start > all.size()) ? List.of() : all.subList(start, end);
        return new PageImpl<>(page, pageable, all.size());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemOrcamentoResponseDTO> listByOrcamento(Integer orcamentoId) {
        return repositoryPort.findByOrcamentoId(orcamentoId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ItemOrcamentoResponseDTO update(Integer id, ItemOrcamentoUpdateDTO dto) {
        ItemOrcamento entity = repositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemOrcamento não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    public ItemOrcamentoResponseDTO replace(Integer id, ItemOrcamentoRequestDTO dto) {
        repositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemOrcamento não encontrado: " + id));
        ItemOrcamento updated = mapper.toEntity(dto);
        updated.setId(id);
        return mapper.toResponse(repositoryPort.save(updated));
    }

    @Override
    public void delete(Integer id) {
        ItemOrcamento entity = repositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemOrcamento não encontrado: " + id));
        repositoryPort.delete(entity);
    }
}
