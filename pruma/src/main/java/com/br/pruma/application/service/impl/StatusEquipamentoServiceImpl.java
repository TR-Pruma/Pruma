package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.StatusEquipamentoRequestDTO;
import com.br.pruma.application.dto.response.StatusEquipamentoResponseDTO;
import com.br.pruma.application.service.StatusEquipamentoService;
import com.br.pruma.core.domain.StatusEquipamento;
import com.br.pruma.core.repository.StatusEquipamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusEquipamentoServiceImpl implements StatusEquipamentoService {

    private final StatusEquipamentoRepository statusEquipamentoRepository;

    @Override
    @Transactional
    public StatusEquipamentoResponseDTO criar(StatusEquipamentoRequestDTO request) {
        StatusEquipamento entity = StatusEquipamento.builder()
                .descricao(request.descricao())
                .status(request.status())
                .build();
        return toResponse(statusEquipamentoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatusEquipamentoResponseDTO> listarTodos() {
        return statusEquipamentoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StatusEquipamentoResponseDTO buscarPorId(Integer id) {
        return statusEquipamentoRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("StatusEquipamento não encontrado: " + id));
    }

    @Override
    @Transactional
    public StatusEquipamentoResponseDTO atualizar(Integer id, StatusEquipamentoRequestDTO request) {
        StatusEquipamento entity = statusEquipamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StatusEquipamento não encontrado: " + id));
        entity.setDescricao(request.descricao());
        entity.setStatus(request.status());
        return toResponse(statusEquipamentoRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!statusEquipamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("StatusEquipamento não encontrado: " + id);
        }
        statusEquipamentoRepository.deleteById(id);
    }

    private StatusEquipamentoResponseDTO toResponse(StatusEquipamento e) {
        return new StatusEquipamentoResponseDTO(
                e.getId(),
                e.getDescricao(),
                e.getStatus(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
