package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.PosObraRequestDTO;
import com.br.pruma.application.dto.response.PosObraResponseDTO;
import com.br.pruma.application.dto.update.PosObraUpdateDTO;
import com.br.pruma.application.mapper.PosObraMapper;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.PosObra;
import com.br.pruma.core.repository.ObraRepository;
import com.br.pruma.core.repository.PosObraRepository;
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
public class PosObraService {

    private final PosObraRepository repository;
    private final ObraRepository obraRepository;
    private final PosObraMapper mapper;

    public PosObraResponseDTO create(PosObraRequestDTO dto) {
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
        PosObra entity = mapper.toEntity(dto);
        entity.setObra(obra);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public PosObraResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pós-Obra não encontrada: " + id)));
    }

    @Transactional(readOnly = true)
    public List<PosObraResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<PosObraResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<PosObraResponseDTO> listByObra(Integer obraId) {
        return repository.findAllByObra_Id(obraId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public PosObraResponseDTO update(Integer id, PosObraUpdateDTO dto) {
        PosObra entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pós-Obra não encontrada: " + id));
        if (dto.getObraId() != null) {
            Obra obra = obraRepository.findById(dto.getObraId())
                    .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
            entity.setObra(obra);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public PosObraResponseDTO replace(Integer id, PosObraRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pós-Obra não encontrada: " + id));
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
        PosObra entity = mapper.toEntity(dto);
        entity.setId(id);
        entity.setObra(obra);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Pós-Obra não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
