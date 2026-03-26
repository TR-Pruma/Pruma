package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.RequisicaoMaterialRequestDTO;
import com.br.pruma.application.dto.response.RequisicaoMaterialResponseDTO;
import com.br.pruma.application.dto.update.RequisicaoMaterialUpdateDTO;
import com.br.pruma.application.mapper.RequisicaoMaterialMapper;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.RequisicaoMaterial;
import com.br.pruma.core.repository.ObraRepository;
import com.br.pruma.core.repository.RequisicaoMaterialRepository;
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
public class RequisicaoMaterialService {

    private final RequisicaoMaterialRepository repository;
    private final ObraRepository obraRepository;
    private final RequisicaoMaterialMapper mapper;

    public RequisicaoMaterialResponseDTO create(RequisicaoMaterialRequestDTO dto) {
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
        RequisicaoMaterial entity = mapper.toEntity(dto);
        entity.setObra(obra);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public RequisicaoMaterialResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Requisição de Material não encontrada: " + id)));
    }

    @Transactional(readOnly = true)
    public List<RequisicaoMaterialResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<RequisicaoMaterialResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<RequisicaoMaterialResponseDTO> listByObra(Integer obraId) {
        return repository.findAllByObra_Id(obraId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public RequisicaoMaterialResponseDTO update(Integer id, RequisicaoMaterialUpdateDTO dto) {
        RequisicaoMaterial entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Requisição de Material não encontrada: " + id));
        if (dto.getObraId() != null) {
            Obra obra = obraRepository.findById(dto.getObraId())
                    .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
            entity.setObra(obra);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public RequisicaoMaterialResponseDTO replace(Integer id, RequisicaoMaterialRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Requisição de Material não encontrada: " + id));
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
        RequisicaoMaterial entity = mapper.toEntity(dto);
        entity.setId(id);
        entity.setObra(obra);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Requisição de Material não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
