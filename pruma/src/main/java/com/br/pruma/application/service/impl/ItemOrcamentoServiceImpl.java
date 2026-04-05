package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ItemOrcamentoRequestDTO;
import com.br.pruma.application.dto.response.ItemOrcamentoResponseDTO;
import com.br.pruma.application.dto.update.ItemOrcamentoUpdateDTO;
import com.br.pruma.application.mapper.ItemOrcamentoMapper;
import com.br.pruma.application.service.ItemOrcamentoService;
import com.br.pruma.core.domain.ItemOrcamento;
import com.br.pruma.core.repository.ItemOrcamentoRepository;
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

    private final ItemOrcamentoRepository itemOrcamentoRepository;
    private final ItemOrcamentoMapper mapper;

    @Override
    public ItemOrcamentoResponseDTO create(ItemOrcamentoRequestDTO dto) {
        ItemOrcamento entity = mapper.toEntity(dto);
        return mapper.toResponse(itemOrcamentoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ItemOrcamentoResponseDTO getById(Integer id) {
        return mapper.toResponse(itemOrcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemOrcamento não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemOrcamentoResponseDTO> listAll() {
        return itemOrcamentoRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemOrcamentoResponseDTO> list(Pageable pageable) {
        return itemOrcamentoRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemOrcamentoResponseDTO> listByOrcamento(Integer orcamentoId) {
        return itemOrcamentoRepository.findByOrcamento_Id(orcamentoId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ItemOrcamentoResponseDTO update(Integer id, ItemOrcamentoUpdateDTO dto) {
        ItemOrcamento entity = itemOrcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemOrcamento não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(itemOrcamentoRepository.save(entity));
    }

    @Override
    public ItemOrcamentoResponseDTO replace(Integer id, ItemOrcamentoRequestDTO dto) {
        itemOrcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemOrcamento não encontrado: " + id));
        ItemOrcamento updated = mapper.toEntity(dto);
        updated.setId(id);
        return mapper.toResponse(itemOrcamentoRepository.save(updated));
    }

    @Override
    public void delete(Integer id) {
        ItemOrcamento entity = itemOrcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemOrcamento não encontrado: " + id));
        itemOrcamentoRepository.delete(entity);
    }
}
