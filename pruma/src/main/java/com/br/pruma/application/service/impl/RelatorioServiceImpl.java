package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.RelatorioRequestDTO;
import com.br.pruma.application.dto.response.RelatorioResponseDTO;
import com.br.pruma.application.dto.update.RelatorioUpdateDTO;
import com.br.pruma.application.mapper.RelatorioMapper;
import com.br.pruma.application.service.RelatorioService;
import com.br.pruma.core.domain.Relatorio;
import com.br.pruma.core.repository.RelatorioRepository;
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
public class RelatorioServiceImpl implements RelatorioService {

    private final RelatorioRepository repository;
    private final RelatorioMapper mapper;

    @Override
    public RelatorioResponseDTO create(RelatorioRequestDTO dto) {
        Relatorio entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public RelatorioResponseDTO getById(Integer id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Relatório não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelatorioResponseDTO> listAll() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RelatorioResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelatorioResponseDTO> listByProjeto(Integer obraId) {
        return mapper.toDTOList(repository.findByObraId(obraId));
    }

    @Override
    public RelatorioResponseDTO update(Integer id, RelatorioUpdateDTO dto) {
        Relatorio entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Relatório não encontrado: " + id));
        mapper.updateFromDto(dto, entity);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public RelatorioResponseDTO replace(Integer id, RelatorioRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Relatório não encontrado: " + id));
        Relatorio entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        Relatorio entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Relatório não encontrado: " + id));
        repository.delete(entity);
    }
}