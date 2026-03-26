package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.PreObraRequestDTO;
import com.br.pruma.application.dto.response.PreObraResponseDTO;
import com.br.pruma.application.dto.update.PreObraUpdateDTO;
import com.br.pruma.application.mapper.PreObraMapper;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.PreObra;
import com.br.pruma.core.repository.ObraRepository;
import com.br.pruma.core.repository.PreObraRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PreObraService {

    private final PreObraRepository repository;
    private final ObraRepository obraRepository;
    private final PreObraMapper mapper;

    public PreObraResponseDTO create(PreObraRequestDTO dto) {
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
        PreObra entity = mapper.toEntity(dto);
        entity.setObra(obra);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public PreObraResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pré-Obra não encontrada: " + id)));
    }

    @Transactional(readOnly = true)
    public List<PreObraResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PreObraResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<PreObraResponseDTO> listByObra(Integer obraId) {
        return repository.findAllByObra_Id(obraId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public PreObraResponseDTO update(Integer id, PreObraUpdateDTO dto) {
        PreObra entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pré-Obra não encontrada: " + id));
        if (dto.getObraId() != null) {
            Obra obra = obraRepository.findById(dto.getObraId())
                    .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
            entity.setObra(obra);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public PreObraResponseDTO replace(Integer id, PreObraRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pré-Obra não encontrada: " + id));
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
        PreObra entity = mapper.toEntity(dto);
        entity.setId(id);
        entity.setObra(obra);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Pré-Obra não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
