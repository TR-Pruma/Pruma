package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoResponseDTO;
import com.br.pruma.application.mapper.EquipamentoMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.br.pruma.core.repository.EquipamentoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipamentoService {
    private final EquipamentoRepository repository;
    private final EquipamentoMapper mapper;

    public EquipamentoResponseDTO criar(EquipamentoRequestDTO dto) {
        var entity = mapper.toEntity(dto);
        return mapper.toResponseDTO(repository.save(entity));
    }
    public List<EquipamentoResponseDTO> listar() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }
    public EquipamentoResponseDTO buscarPorId(Integer id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
        return mapper.toResponseDTO(entity);
    }
    public EquipamentoResponseDTO atualizar(Integer id, EquipamentoRequestDTO dto) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
        mapper.updateEntityFromDTO(dto, entity);
        return mapper.toResponseDTO(repository.save(entity));
    }
    public void deletar(Integer id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));
        repository.delete(entity);
    }
}
