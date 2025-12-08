package com.br.pruma.application.service;

import org.springframework.transaction.annotation.Transactional;
import com.br.pruma.application.dto.request.EquipamentoProjetoRequestDTO;
import com.br.pruma.application.dto.update.EquipamentoProjetoResponseDTO;
import com.br.pruma.application.mapper.EquipamentoProjetoMapper;
import com.br.pruma.core.domain.EquipamentoProjeto;
import com.br.pruma.core.repository.EquipamentoProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipamentoProjetoService {
    private final EquipamentoProjetoRepository repository;
    private final EquipamentoProjetoMapper mapper;

    @Transactional
    public EquipamentoProjetoResponseDTO criar(EquipamentoProjetoRequestDTO dto) {

        boolean existeConflito = repository.existsByEquipamentoIdAndDataAlocacao(
                dto.getEquipamentoId(),
                dto.getDataAlocacao()
        );

        if (existeConflito) {
            throw new IllegalArgumentException("Equipamento já alocado nesta data");
        }

        EquipamentoProjeto entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public EquipamentoProjetoResponseDTO buscarPorId(Long id) {
        EquipamentoProjeto entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EquipamentoProjeto não encontrado"));
        return mapper.toResponseDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<EquipamentoProjetoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public EquipamentoProjetoResponseDTO atualizar(Long id, EquipamentoProjetoRequestDTO dto) {
        EquipamentoProjeto entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EquipamentoProjeto não encontrado"));

        entity.setEquipamento(entity.getEquipamento()); // pode ajustar via mapper
        entity.setProjeto(entity.getProjeto());
        entity.setDataAlocacao(dto.getDataAlocacao());

        repository.save(entity);
        return mapper.toResponseDTO(entity);
    }

    @Transactional
    public void deletar(Long id) {
        EquipamentoProjeto entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EquipamentoProjeto não encontrado"));
        repository.delete(entity);
    }
}