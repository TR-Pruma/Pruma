package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ItemOrcamentoRequestDTO;
import com.br.pruma.application.dto.response.ItemOrcamentoResponseDTO;
import com.br.pruma.application.dto.update.ItemOrcamentoUpdateDTO;
import com.br.pruma.application.mapper.ItemOrcamentoMapper;
import com.br.pruma.application.service.ItemOrcamentoService;
import com.br.pruma.config.Constantes;
import com.br.pruma.core.domain.ItemOrcamento;
import com.br.pruma.core.repository.port.ItemOrcamentoRepositoryPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemOrcamentoServiceImpl implements ItemOrcamentoService {

    private final ItemOrcamentoRepositoryPort itemOrcamentoRepositoryPort;
    private final ItemOrcamentoMapper itemOrcamentoMapper;

    @Override
    public ItemOrcamentoResponseDTO create(ItemOrcamentoRequestDTO dto) {
        ItemOrcamento itemOrcamento = itemOrcamentoMapper.toEntity(dto);
        ItemOrcamento saved = itemOrcamentoRepositoryPort.save(itemOrcamento);
        return itemOrcamentoMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemOrcamentoResponseDTO getById(Integer id) {
        ItemOrcamento itemOrcamento = itemOrcamentoRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.ITEM_ORCAMENTO_NAO_ENCONTRADO + id));
        return itemOrcamentoMapper.toResponse(itemOrcamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemOrcamentoResponseDTO> listAll() {
        return itemOrcamentoRepositoryPort.findAll()
                .stream()
                .map(itemOrcamentoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemOrcamentoResponseDTO> list(Pageable pageable) {
        return itemOrcamentoRepositoryPort.findAll(pageable)
                .map(itemOrcamentoMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemOrcamentoResponseDTO> listByOrcamento(Integer orcamentoId) {
        return itemOrcamentoRepositoryPort.findAllByOrcamento_Id(orcamentoId)
                .stream()
                .map(itemOrcamentoMapper::toResponse)
                .toList();
    }

    @Override
    public ItemOrcamentoResponseDTO update(Integer id, ItemOrcamentoUpdateDTO dto) {
        ItemOrcamento itemOrcamento = itemOrcamentoRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.ITEM_ORCAMENTO_NAO_ENCONTRADO + id));
        itemOrcamentoMapper.updateFromDto(dto, itemOrcamento);
        ItemOrcamento updated = itemOrcamentoRepositoryPort.save(itemOrcamento);
        return itemOrcamentoMapper.toResponse(updated);
    }

    @Override
    public ItemOrcamentoResponseDTO replace(Integer id, ItemOrcamentoRequestDTO dto) {
        itemOrcamentoRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.ITEM_ORCAMENTO_NAO_ENCONTRADO + id));
        ItemOrcamento itemOrcamento = itemOrcamentoMapper.toEntity(dto);
        itemOrcamento.setId(id);
        ItemOrcamento replaced = itemOrcamentoRepositoryPort.save(itemOrcamento);
        return itemOrcamentoMapper.toResponse(replaced);
    }

    @Override
    public void delete(Integer id) {
        ItemOrcamento itemOrcamento = itemOrcamentoRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Constantes.ITEM_ORCAMENTO_NAO_ENCONTRADO + id));
        itemOrcamentoRepositoryPort.delete(itemOrcamento);
    }
}
