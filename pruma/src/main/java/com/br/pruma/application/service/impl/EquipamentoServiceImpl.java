package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.EquipamentoRequestDTO;
import com.br.pruma.application.dto.response.EquipamentoResponseDTO;
import com.br.pruma.application.service.EquipamentoService;
import com.br.pruma.core.domain.Equipamento;
import com.br.pruma.core.repository.EquipamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipamentoServiceImpl implements EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;

    @Override
    @Transactional
    public EquipamentoResponseDTO criar(EquipamentoRequestDTO request) {
        Equipamento entity = Equipamento.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .marca(request.marca())
                .modelo(request.modelo())
                .build();
        return toResponse(equipamentoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipamentoResponseDTO> listarTodos() {
        return equipamentoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EquipamentoResponseDTO buscarPorId(Integer id) {
        return equipamentoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado: " + id));
    }

    @Override
    @Transactional
    public EquipamentoResponseDTO atualizar(Integer id, EquipamentoRequestDTO request) {
        Equipamento entity = equipamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado: " + id));
        entity.setNome(request.nome());
        entity.setDescricao(request.descricao());
        entity.setMarca(request.marca());
        entity.setModelo(request.modelo());
        return toResponse(equipamentoRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!equipamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Equipamento não encontrado: " + id);
        }
        equipamentoRepository.deleteById(id);
    }

    private EquipamentoResponseDTO toResponse(Equipamento e) {
        return new EquipamentoResponseDTO(
                e.getId(),
                e.getNome(),
                e.getDescricao(),
                e.getMarca(),
                e.getModelo(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
