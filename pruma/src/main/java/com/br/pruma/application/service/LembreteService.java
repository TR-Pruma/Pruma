package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.LembreteRequestDTO;
import com.br.pruma.application.dto.response.LembreteResponseDTO;
import com.br.pruma.application.dto.update.LembreteUpdateDTO;
import com.br.pruma.application.mapper.LembreteMapper;
import com.br.pruma.core.domain.Lembrete;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.LembreteRepository;
import com.br.pruma.core.repository.ProjetoRepository;
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
public class LembreteService {

    private final LembreteRepository repository;
    private final ProjetoRepository projetoRepository;
    private final LembreteMapper mapper;

    public LembreteResponseDTO create(LembreteRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
        Lembrete entity = mapper.toEntity(dto);
        entity.setProjeto(projeto);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public LembreteResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lembrete não encontrado: " + id)));
    }

    @Transactional(readOnly = true)
    public List<LembreteResponseDTO> listAll() {
        return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<LembreteResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public List<LembreteResponseDTO> listByProjeto(Integer projetoId) {
        return repository.findAllByProjeto_Id(projetoId).stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public LembreteResponseDTO update(Integer id, LembreteUpdateDTO dto) {
        Lembrete entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lembrete não encontrado: " + id));
        if (dto.getProjetoId() != null) {
            Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                    .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
            entity.setProjeto(projeto);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    public LembreteResponseDTO replace(Integer id, LembreteRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lembrete não encontrado: " + id));
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado: " + dto.getProjetoId()));
        Lembrete entity = mapper.toEntity(dto);
        entity.setId(id);
        entity.setProjeto(projeto);
        return mapper.toResponse(repository.save(entity));
    }

    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Lembrete não encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
