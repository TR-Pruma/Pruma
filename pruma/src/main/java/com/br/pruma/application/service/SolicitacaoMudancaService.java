package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.SolicitacaoMudancaRequestDTO;
import com.br.pruma.application.dto.response.SolicitacaoMudancaResponseDTO;
import com.br.pruma.application.dto.update.SolicitacaoMudancaUpdateDTO;
import com.br.pruma.application.mapper.SolicitacaoMudancaMapper;
import com.br.pruma.core.domain.Obra;
import com.br.pruma.core.domain.SolicitacaoMudanca;
import com.br.pruma.core.repository.ObraRepository;
import com.br.pruma.core.repository.SolicitacaoMudancaRepository;
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
public class SolicitacaoMudancaService {

    private final SolicitacaoMudancaRepository repository;
    private final ObraRepository obraRepository;
    private final SolicitacaoMudancaMapper mapper;

    public SolicitacaoMudancaResponseDTO create(SolicitacaoMudancaRequestDTO dto) {
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
        SolicitacaoMudanca entity = mapper.toEntity(dto);
        entity.setObra(obra);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public SolicitacaoMudancaResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação de Mudança não encontrada: " + id)));
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoMudancaResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<SolicitacaoMudancaResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoMudancaResponseDTO> listByObra(Integer obraId) {
        return repository.findAllByObra_Id(obraId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public SolicitacaoMudancaResponseDTO update(Integer id, SolicitacaoMudancaUpdateDTO dto) {
        SolicitacaoMudanca entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação de Mudança não encontrada: " + id));
        if (dto.getObraId() != null) {
            Obra obra = obraRepository.findById(dto.getObraId())
                    .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
            entity.setObra(obra);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public SolicitacaoMudancaResponseDTO replace(Integer id, SolicitacaoMudancaRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitação de Mudança não encontrada: " + id));
        Obra obra = obraRepository.findById(dto.getObraId())
                .orElseThrow(() -> new EntityNotFoundException("Obra não encontrada: " + dto.getObraId()));
        SolicitacaoMudanca entity = mapper.toEntity(dto);
        entity.setId(id);
        entity.setObra(obra);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Solicitação de Mudança não encontrada: " + id);
        }
        repository.deleteById(id);
    }
}
