package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.InspecaoRequestDTO;
import com.br.pruma.application.dto.response.InspecaoResponseDTO;
import com.br.pruma.application.dto.update.InspecaoUpdateDTO;
import com.br.pruma.application.mapper.InspecaoMapper;
import com.br.pruma.application.service.InspecaoService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Inspecao;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.repository.InspecaoRepository;
import com.br.pruma.core.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InspecaoServiceImpl implements InspecaoService {

    private final InspecaoRepository repository;
    private final ProjetoRepository projetoRepository;
    private final InspecaoMapper mapper;

    @Override
    public InspecaoResponseDTO create(InspecaoRequestDTO dto) {
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Projeto com ID " + dto.getProjetoId() + " não encontrado."));
        Inspecao entity = mapper.toEntity(dto);
        entity.setProjeto(projeto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public InspecaoResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Inspeção com ID " + id + " não encontrada.")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<InspecaoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InspecaoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InspecaoResponseDTO> listByProjeto(Integer projetoId) {
        return repository.findAllByProjeto_Id(projetoId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public InspecaoResponseDTO update(Integer id, InspecaoUpdateDTO dto) {
        Inspecao entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Inspeção com ID " + id + " não encontrada."));
        if (dto.getProjetoId() != null) {
            Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Projeto com ID " + dto.getProjetoId() + " não encontrado."));
            entity.setProjeto(projeto);
        }
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public InspecaoResponseDTO replace(Integer id, InspecaoRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Inspeção com ID " + id + " não encontrada."));
        Projeto projeto = projetoRepository.findById(dto.getProjetoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Projeto com ID " + dto.getProjetoId() + " não encontrado."));
        Inspecao entity = mapper.toEntity(dto);
        entity.setId(id);
        entity.setProjeto(projeto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        Inspecao entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Inspeção com ID " + id + " não encontrada."));
        repository.delete(entity);
    }
}
