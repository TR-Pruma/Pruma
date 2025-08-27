package com.br.pruma.application.service;


import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.update.EquipamentoAtivoUpdateDTO;
import com.br.pruma.application.dto.response.EquipamentoListDTO;
import com.br.pruma.application.dto.response.EquipamentoResponseDTO;
import com.br.pruma.application.dto.update.EquipamentoStatusUpdateDTO;
import com.br.pruma.application.mapper.EquipamentoMapper;
import com.br.pruma.core.domain.Equipamento;
import com.br.pruma.core.enums.StatusEquipamento;
import com.br.pruma.infra.repository.EquipamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EquipamentoService {

    private final EquipamentoRepository repository;
    private final EquipamentoMapper mapper;

    @Transactional
    public EquipamentoResponseDTO criar(EquipamentoRequestDTO dto) {
        Equipamento entity = mapper.toEntity(dto);
        return mapper.toResponseDto(repository.save(entity));
    }

    @Transactional
    public EquipamentoResponseDTO atualizar(Integer id, EquipamentoRequestDTO dto) {
        Equipamento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
        mapper.updateEntityFromDto(dto, entity);
        return mapper.toResponseDto(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public EquipamentoResponseDTO buscarPorId(Integer id) {
        Equipamento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
        return mapper.toResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<EquipamentoListDTO> listar(String nome, StatusEquipamento status, Boolean ativo, Pageable pageable) {
        return repository.searchIncludingInativos(nome, status, ativo, null, null, pageable)
                .map(mapper::toListDto);
    }

    @Transactional
    public void atualizarStatus(Integer id, EquipamentoStatusUpdateDTO dto) {
        Equipamento entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
        mapper.updateStatusFromDto(dto, entity);
        repository.save(entity);
    }

    @Transactional
    public void atualizarAtivo(Integer id, EquipamentoAtivoUpdateDTO dto) {
        Equipamento entity = repository.findByIdIncludingInativos(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
        mapper.updateAtivoFromDto(dto, entity);
        repository.save(entity);
    }

    @Transactional
    public void deletar(Integer id) {
        repository.deleteById(id); // Soft delete pelo @SQLDelete
    }

    @Transactional
    public void inativarEmLote(List<Integer> ids) {
        repository.softDeleteManyByIds(ids);
    }

    @Transactional
    public void reativarEmLote(List<Integer> ids) {
        repository.reativarManyByIds(ids);
    }
}