package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.AuditoriaRequestDTO;
import com.br.pruma.application.dto.response.AuditoriaResponseDTO;
import com.br.pruma.application.service.AuditoriaService;
import com.br.pruma.core.domain.Auditoria;
import com.br.pruma.core.repository.AuditoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditoriaServiceImpl implements AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    @Override
    @Transactional
    public AuditoriaResponseDTO criar(AuditoriaRequestDTO request) {
        Auditoria entity = Auditoria.builder()
                .descricao(request.descricao())
                .resultado(request.resultado())
                .build();
        return toResponse(auditoriaRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditoriaResponseDTO> listarTodos() {
        return auditoriaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AuditoriaResponseDTO buscarPorId(Integer id) {
        return auditoriaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Auditoria não encontrada: " + id));
    }

    @Override
    @Transactional
    public AuditoriaResponseDTO atualizar(Integer id, AuditoriaRequestDTO request) {
        Auditoria entity = auditoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Auditoria não encontrada: " + id));
        entity.setDescricao(request.descricao());
        entity.setResultado(request.resultado());
        return toResponse(auditoriaRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!auditoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Auditoria não encontrada: " + id);
        }
        auditoriaRepository.deleteById(id);
    }

    private AuditoriaResponseDTO toResponse(Auditoria e) {
        return new AuditoriaResponseDTO(
                e.getId(),
                e.getDescricao(),
                e.getResultado(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
