package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.SubContratoRequestDTO;
import com.br.pruma.application.dto.response.SubContratoResponseDTO;
import com.br.pruma.application.dto.update.SubContratoUpdateDTO;
import com.br.pruma.application.mapper.SubContratoMapper;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.SubContrato;
import com.br.pruma.core.repository.ObraRepository;
import com.br.pruma.core.repository.SubContratoRepository;
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
public class SubContratoService {

    private final SubContratoRepository repository;
    private final ObraRepository obraRepository;
    private final SubContratoMapper mapper;

    public SubContratoResponseDTO create(SubContratoRequestDTO dto) {
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
        SubContrato entity = mapper.toEntity(dto);
        entity.setObra(obra);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public SubContratoResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SubContrato não encontrado: " + id)));
    }

    @Transactional(readOnly = true)
    public List<SubContratoResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<SubContratoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<SubContratoResponseDTO> listByObra(Integer obraId) {
        return repository.findAllByObra_Id(obraId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public SubContratoResponseDTO update(Integer id, SubContratoUpdateDTO dto) {
        SubContrato entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SubContrato não encontrado: " + id));
        if (dto.getObraId() != null) {
            Obra obra = obraRepository.findById(dto.getObraId())
                    .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
            entity.setObra(obra);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public SubContratoResponseDTO replace(Integer id, SubContratoRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SubContrato não encontrado: " + id));
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
        SubContrato entity = mapper.toEntity(dto);
        entity.setId(id);
        entity.setObra(obra);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("SubContrato não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
