package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ItemOrcamentoRequestDTO;
import com.br.pruma.application.dto.response.ItemOrcamentoResponseDTO;
import com.br.pruma.application.dto.update.ItemOrcamentoUpdateDTO;
import com.br.pruma.application.mapper.ItemOrcamentoMapper;
import com.br.pruma.core.domain.ItemOrcamento;
import com.br.pruma.core.domain.Orcamento;
import com.br.pruma.core.repository.ItemOrcamentoRepository;
import com.br.pruma.core.repository.OrcamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemOrcamentoService {

    private final ItemOrcamentoRepository repository;
    private final OrcamentoRepository orcamentoRepository;
    private final ItemOrcamentoMapper mapper;


    public ItemOrcamentoResponseDTO create(ItemOrcamentoRequestDTO dto) {
        Orcamento orcamento = orcamentoRepository.findById(dto.getOrcamentoId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Orçamento não encontrado: " + dto.getOrcamentoId())
                );

        ItemOrcamento entity = mapper.toEntity(dto);
        entity.setOrcamento(orcamento);

        ItemOrcamento saved = repository.save(entity);
        return mapper.toResponse(saved);
    }
    /**
     * Busca um item pelo ID.
     */
    @Transactional(readOnly = true)
    public ItemOrcamentoResponseDTO getById(Integer id) {
        ItemOrcamento entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("ItemOrcamento não encontrado: " + id)
                );
        return mapper.toResponse(entity);
    }
    /**
     * Lista todos os itens de um determinado orçamento.
     */
    @Transactional(readOnly = true)
    public List<ItemOrcamentoResponseDTO> listByOrcamento(Integer orcamentoId) {
        return repository.findByOrcamento_Id(orcamentoId)
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza campos de um item existente.
     */
    public ItemOrcamentoResponseDTO update(Integer id, ItemOrcamentoUpdateDTO dto) {
        ItemOrcamento entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("ItemOrcamento não encontrado: " + id)
                );

        mapper.updateFromDto(dto, entity);
        ItemOrcamento updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    /**
     * Remove (hard delete) um item pelo ID.
     */
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("ItemOrcamento não encontrado: " + id);
        }
        repository.deleteById(id);
    }


}
