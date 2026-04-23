package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ProjetoCategoriaRequestDTO;
import com.br.pruma.application.dto.response.ProjetoCategoriaResponseDTO;
import com.br.pruma.application.dto.update.ProjetoCategoriaUpdateDTO;
import com.br.pruma.application.mapper.ProjetoCategoriaMapper;
import com.br.pruma.application.service.ProjetoCategoriaService;
import com.br.pruma.core.domain.ProjetoCategoria;
import com.br.pruma.core.repository.ProjetoCategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjetoCategoriaServiceImpl implements ProjetoCategoriaService {

    private final ProjetoCategoriaRepository repository;
    private final ProjetoCategoriaMapper mapper;

    @Override
    public ProjetoCategoriaResponseDTO create(ProjetoCategoriaRequestDTO dto) {
        ProjetoCategoria entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ProjetoCategoriaResponseDTO getById(Integer id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ProjetoCategoria não encontrada: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjetoCategoriaResponseDTO> listAll() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjetoCategoriaResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    /**
     * TODO: ProjetoCategoria não possui FK para Projeto no modelo atual.
     * Implementação provisória — requer revisão do domínio para ser funcional.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjetoCategoriaResponseDTO> listByProjeto(Integer projetoId) {
        return List.of();
    }

    @Override
    public ProjetoCategoriaResponseDTO update(Integer id, ProjetoCategoriaUpdateDTO dto) {
        ProjetoCategoria entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ProjetoCategoria não encontrada: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        ProjetoCategoria entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ProjetoCategoria não encontrada: " + id));
        entity.setAtivo(false);
        repository.save(entity);
    }
}