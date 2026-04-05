package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ItemOrcamentoRequestDTO;
import com.br.pruma.application.dto.response.ItemOrcamentoResponseDTO;
import com.br.pruma.application.dto.update.ItemOrcamentoUpdateDTO;
import com.br.pruma.application.service.ItemOrcamentoService;
import com.br.pruma.core.domain.ItemOrcamento;
import com.br.pruma.core.exception.NotFoundException;
import com.br.pruma.core.mapper.ItemOrcamentoMapper;
import com.br.pruma.core.port.out.ItemOrcamentoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOrcamentoServiceImpl implements ItemOrcamentoService {

    private final ItemOrcamentoRepositoryPort repositoryPort;
    private final ItemOrcamentoMapper mapper;

    @Override
    @Transactional
    public ItemOrcamentoResponseDTO create(ItemOrcamentoRequestDTO dto) {
        ItemOrcamento entity = mapper.toEntity(dto);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    public ItemOrcamentoResponseDTO getById(Integer id) {
        return mapper.toResponse(findOrThrow(id));
    }

    @Override
    public List<ItemOrcamentoResponseDTO> listAll() {
        return repositoryPort.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    public Page<ItemOrcamentoResponseDTO> list(Pageable pageable) {
        return repositoryPort.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public List<ItemOrcamentoResponseDTO> listByOrcamento(Integer orcamentoId) {
        return repositoryPort.findByOrcamentoId(orcamentoId).stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional
    public ItemOrcamentoResponseDTO update(Integer id, ItemOrcamentoUpdateDTO dto) {
        ItemOrcamento entity = findOrThrow(id);
        mapper.updateEntity(dto, entity);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    @Transactional
    public ItemOrcamentoResponseDTO replace(Integer id, ItemOrcamentoRequestDTO dto) {
        findOrThrow(id);
        ItemOrcamento entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repositoryPort.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        findOrThrow(id);
        repositoryPort.deleteById(id);
    }

    private ItemOrcamento findOrThrow(Integer id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("ItemOrcamento não encontrado: " + id));
    }
}
