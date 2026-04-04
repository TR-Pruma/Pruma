package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.AuditoriaRequestDTO;
import com.br.pruma.application.dto.response.AuditoriaResponseDTO;
import com.br.pruma.application.mapper.AuditoriaMapper;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.repository.AuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditoriaService {

    private final AuditoriaRepository repository;
    private final AuditoriaMapper mapper;

    @Transactional(readOnly = true)
    public List<AuditoriaResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<AuditoriaResponseDTO> buscarPorId(UUID id) {
        return repository.findById(id).map(mapper::toResponseDTO);
    }

    public AuditoriaResponseDTO criar(AuditoriaRequestDTO dto) {
        return mapper.toResponseDTO(repository.save(mapper.toEntity(dto)));
    }

    public Optional<AuditoriaResponseDTO> atualizar(UUID id, AuditoriaRequestDTO dto) {
        return repository.findById(id).map(existente -> {
            var atualizado = mapper.toEntity(dto);
            atualizado.setId(id);
            return mapper.toResponseDTO(repository.save(atualizado));
        });
    }

    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Auditoria com ID " + id + " não encontrada.");
        }
        repository.deleteById(id);
    }
}
