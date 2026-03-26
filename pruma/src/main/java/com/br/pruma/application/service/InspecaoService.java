package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.InspecaoRequestDTO;
import com.br.pruma.application.dto.response.InspecaoResponseDTO;
import com.br.pruma.application.dto.update.InspecaoUpdateDTO;
import com.br.pruma.application.mapper.InspecaoMapper;
import com.br.pruma.core.domain.Inspecao;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.repository.InspecaoRepository;
import com.br.pruma.core.repository.ObraRepository;
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
public class InspecaoService {

    private final InspecaoRepository repository;
    private final ObraRepository obraRepository;
    private final InspecaoMapper mapper;

    public InspecaoResponseDTO create(InspecaoRequestDTO dto) {
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
        Inspecao entity = mapper.toEntity(dto);
        entity.setObra(obra);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public InspecaoResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inspeção não encontrada: " + id)));
    }

    @Transactional(readOnly = true)
    public List<InspecaoResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<InspecaoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<InspecaoResponseDTO> listByObra(Integer obraId) {
        return repository.findAllByObra_Id(obraId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public InspecaoResponseDTO update(Integer id, InspecaoUpdateDTO dto) {
        Inspecao entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inspeção não encontrada: " + id));
        if (dto.getObraId() != null) {
            Obra obra = obraRepository.findById(dto.getObraId())
                    .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
            entity.setObra(obra);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public InspecaoResponseDTO replace(Integer id, InspecaoRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inspeção não encontrada: " + id));
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
        Inspecao entity = mapper.toEntity(dto);
        entity.setId(id);
        entity.setObra(obra);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Inspeção não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
