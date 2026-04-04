package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ObraRequestDTO;
import com.br.pruma.application.dto.response.ObraResponseDTO;
import com.br.pruma.application.dto.update.ObraUpdateDTO;
import com.br.pruma.application.mapper.ObraMapper;
import com.br.pruma.application.service.ObraService;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.ObraRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ObraServiceImpl implements ObraService {

    private final ObraRepository obraRepository;
    private final ProjetoRepository projetoRepository;
    private final ObraMapper mapper;

    @Override
    public ObraResponseDTO create(ObraRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
        Obra entity = mapper.toEntity(dto);
        entity.setProjeto(projeto);
        return mapper.toResponse(obraRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ObraResponseDTO getById(Integer id) {
        Obra entity = obraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObraResponseDTO> listAll() {
        return obraRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObraResponseDTO> list(Pageable pageable) {
        return obraRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ObraResponseDTO> listByProjeto(Integer projetoId) {
        return obraRepository.findAllByProjeto_Id(projetoId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public ObraResponseDTO update(Integer id, ObraUpdateDTO dto) {
        Obra entity = obraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + id));
        if (dto.getProjetoId() != null) {
            Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                    .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
            entity.setProjeto(projeto);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(obraRepository.save(entity));
    }

    @Override
    public ObraResponseDTO replace(Integer id, ObraRequestDTO dto) {
        Obra existing = obraRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + id));
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
        Obra updated = mapper.toEntity(dto);
        updated.setId(existing.getId());
        updated.setProjeto(projeto);
        return mapper.toResponse(obraRepository.save(updated));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObraResponseDTO> searchByDescricao(String descricao, Pageable pageable) {
        List<Obra> found = obraRepository.findAllByDescricaoContainingIgnoreCase(descricao);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), found.size());
        List<ObraResponseDTO> content = found.subList(start, end).stream().map(mapper::toResponse).collect(Collectors.toList());
        return new PageImpl<>(content, pageable, found.size());
    }

    @Override
    public void delete(Integer id) {
        if (!obraRepository.existsById(id)) {
            throw new EntityNotFoundException("Obra não encontrada: " + id);
        }
        obraRepository.deleteById(id);
    }
}
