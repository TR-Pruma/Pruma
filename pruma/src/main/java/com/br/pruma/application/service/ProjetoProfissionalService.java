package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ProjetoProfissionalRequestDTO;
import com.br.pruma.application.dto.response.ProjetoProfissionalResponseDTO;
import com.br.pruma.application.mapper.ProjetoProfissionalMapper;
import com.br.pruma.core.domain.ProjetoProfissional;
import com.br.pruma.infra.repository.ProjetoProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjetoProfissionalService {

    private final ProjetoProfissionalRepository repository;
    private final ProjetoProfissionalMapper mapper;

    @Transactional(readOnly = true)
    public List<ProjetoProfissionalResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<ProjetoProfissionalResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<ProjetoProfissionalResponseDTO> listByProjetoId(Integer projetoId) {
        return repository.findByProjetoId(projetoId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProjetoProfissionalResponseDTO> listByProfissionalId(Integer profissionalId) {
        return repository.findByProfissionalId(profissionalId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjetoProfissionalResponseDTO getById(Integer id) {
        ProjetoProfissional entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada com id " + id));
        return mapper.toDTO(entity);
    }

    @Transactional
    public ProjetoProfissionalResponseDTO create(ProjetoProfissionalRequestDTO request) {
        ProjetoProfissional entity = mapper.toEntity(request);
        ProjetoProfissional saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Transactional
    public ProjetoProfissionalResponseDTO update(Integer id, ProjetoProfissionalRequestDTO request) {
        ProjetoProfissional existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada com id " + id));
        mapper.updateFromDto(request, existing);
        ProjetoProfissional saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Associação não encontrada com id " + id);
        }
        repository.deleteById(id);
    }
}
