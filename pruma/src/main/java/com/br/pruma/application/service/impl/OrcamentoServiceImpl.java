package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.OrcamentoRequestDTO;
import com.br.pruma.application.dto.response.OrcamentoResponseDTO;
import com.br.pruma.application.dto.update.OrcamentoUpdateDTO;
import com.br.pruma.application.mapper.OrcamentoMapper;
import com.br.pruma.application.service.OrcamentoService;
import com.br.pruma.core.domain.Orcamento;
import com.br.pruma.core.repository.OrcamentoRepository;
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
public class OrcamentoServiceImpl implements OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final OrcamentoMapper mapper;

    @Override
    public OrcamentoResponseDTO create(OrcamentoRequestDTO dto) {
        Orcamento entity = mapper.toEntity(dto);
        return mapper.toResponse(orcamentoRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public OrcamentoResponseDTO getById(Integer id) {
        return mapper.toResponse(orcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orcamento n\u00e3o encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrcamentoResponseDTO> listAll() {
        return orcamentoRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrcamentoResponseDTO> list(Pageable pageable) {
        return orcamentoRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrcamentoResponseDTO> listByProjeto(Integer projetoId) {
        return orcamentoRepository.findAll().stream()
                .filter(o -> o.getProjeto() != null && o.getProjeto().getId().equals(projetoId))
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public OrcamentoResponseDTO update(Integer id, OrcamentoUpdateDTO dto) {
        Orcamento entity = orcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orcamento n\u00e3o encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(orcamentoRepository.save(entity));
    }

    @Override
    public OrcamentoResponseDTO replace(Integer id, OrcamentoRequestDTO dto) {
        orcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orcamento n\u00e3o encontrado: " + id));
        Orcamento updated = mapper.toEntity(dto);
        updated.setId(id);
        return mapper.toResponse(orcamentoRepository.save(updated));
    }

    @Override
    public void delete(Integer id) {
        Orcamento entity = orcamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orcamento n\u00e3o encontrado: " + id));
        orcamentoRepository.delete(entity);
    }
}
