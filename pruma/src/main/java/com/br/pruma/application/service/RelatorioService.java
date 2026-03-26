package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.RelatorioRequestDTO;
import com.br.pruma.application.dto.response.RelatorioResponseDTO;
import com.br.pruma.application.dto.update.RelatorioUpdateDTO;
import com.br.pruma.application.mapper.RelatorioMapper;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.domain.Relatorio;
import com.br.pruma.core.repository.ProjetoRepository;
import com.br.pruma.core.repository.RelatorioRepository;
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
public class RelatorioService {

    private final RelatorioRepository repository;
    private final ProjetoRepository projetoRepository;
    private final RelatorioMapper mapper;

    public RelatorioResponseDTO create(RelatorioRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
        Relatorio entity = mapper.toEntity(dto);
        entity.setProjeto(projeto);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public RelatorioResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relatório não encontrado: " + id)));
    }

    @Transactional(readOnly = true)
    public List<RelatorioResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<RelatorioResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<RelatorioResponseDTO> listByProjeto(Integer projetoId) {
        return repository.findAllByProjeto_Id(projetoId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public RelatorioResponseDTO update(Integer id, RelatorioUpdateDTO dto) {
        Relatorio entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relatório não encontrado: " + id));
        if (dto.getProjetoId() != null) {
            Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                    .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
            entity.setProjeto(projeto);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public RelatorioResponseDTO replace(Integer id, RelatorioRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Relatório não encontrado: " + id));
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
        Relatorio entity = mapper.toEntity(dto);
        entity.setId(id);
        entity.setProjeto(projeto);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Relatório não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
