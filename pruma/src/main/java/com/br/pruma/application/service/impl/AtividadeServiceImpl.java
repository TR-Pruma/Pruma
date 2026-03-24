package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.AtividadeRequestDTO;
import com.br.pruma.application.dto.response.AtividadeResponseDTO;
import com.br.pruma.application.service.AtividadeService;
import com.br.pruma.core.domain.Atividade;
import com.br.pruma.core.repository.AtividadeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtividadeServiceImpl implements AtividadeService {

    private final AtividadeRepository atividadeRepository;

    @Override
    @Transactional
    public AtividadeResponseDTO criar(AtividadeRequestDTO request) {
        Atividade entity = Atividade.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .status(request.status())
                .build();
        return toResponse(atividadeRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AtividadeResponseDTO> listarTodos() {
        return atividadeRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AtividadeResponseDTO buscarPorId(Integer id) {
        return atividadeRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada: " + id));
    }

    @Override
    @Transactional
    public AtividadeResponseDTO atualizar(Integer id, AtividadeRequestDTO request) {
        Atividade entity = atividadeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atividade não encontrada: " + id));
        entity.setNome(request.nome());
        entity.setDescricao(request.descricao());
        entity.setStatus(request.status());
        return toResponse(atividadeRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!atividadeRepository.existsById(id)) {
            throw new EntityNotFoundException("Atividade não encontrada: " + id);
        }
        atividadeRepository.deleteById(id);
    }

    private AtividadeResponseDTO toResponse(Atividade e) {
        return new AtividadeResponseDTO(
                e.getId(),
                e.getNome(),
                e.getDescricao(),
                e.getStatus(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
