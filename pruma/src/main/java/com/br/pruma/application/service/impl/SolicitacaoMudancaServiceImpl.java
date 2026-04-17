package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.SolicitacaoMudancaRequestDTO;
import com.br.pruma.application.dto.response.SolicitacaoMudancaResponseDTO;
import com.br.pruma.application.dto.update.SolicitacaoMudancaUpdateDTO;
import com.br.pruma.application.mapper.SolicitacaoMudancaMapper;
import com.br.pruma.application.service.SolicitacaoMudancaService;
import com.br.pruma.core.domain.Projeto;
import com.br.pruma.core.domain.SolicitacaoMudanca;
import com.br.pruma.core.domain.StatusSolicitacao;
import com.br.pruma.core.repository.ProjetoRepository;
import com.br.pruma.core.repository.SolicitacaoMudancaRepository;
import com.br.pruma.core.repository.StatusSolicitacaoRepository;
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
public class SolicitacaoMudancaServiceImpl implements SolicitacaoMudancaService {

    private final SolicitacaoMudancaRepository repository;
    private final SolicitacaoMudancaMapper mapper;
    private final ProjetoRepository projetoRepository;
    private final StatusSolicitacaoRepository statusSolicitacaoRepository;

    @Override
    public SolicitacaoMudancaResponseDTO create(SolicitacaoMudancaRequestDTO dto) {
        SolicitacaoMudanca entity = mapper.toEntity(dto);

        Projeto projeto = projetoRepository.findById(dto.projetoId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Projeto não encontrado: " + dto.projetoId()));

        StatusSolicitacao status = statusSolicitacaoRepository.findById(dto.statusSolicitacaoId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "StatusSolicitacao não encontrado: " + dto.statusSolicitacaoId()));

        entity.setProjeto(projeto);
        entity.setStatusSolicitacao(status);

        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public SolicitacaoMudancaResponseDTO getById(Integer id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "SolicitacaoMudanca não encontrada: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitacaoMudancaResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SolicitacaoMudancaResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitacaoMudancaResponseDTO> listByProjeto(Integer projetoId) {
        return repository.findByProjetoId(projetoId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public SolicitacaoMudancaResponseDTO update(Integer id, SolicitacaoMudancaUpdateDTO dto) {
        SolicitacaoMudanca entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "SolicitacaoMudanca não encontrada: " + id));

        if (dto.projetoId() != null) {
            Projeto projeto = projetoRepository.findById(dto.projetoId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Projeto não encontrado: " + dto.projetoId()));
            entity.setProjeto(projeto);
        }

        if (dto.statusSolicitacaoId() != null) {
            StatusSolicitacao status = statusSolicitacaoRepository.findById(dto.statusSolicitacaoId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "StatusSolicitacao não encontrado: " + dto.statusSolicitacaoId()));
            entity.setStatusSolicitacao(status);
        }

        mapper.updateFromDto(dto, entity); // resolve os campos simples, ignora nulos

        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public SolicitacaoMudancaResponseDTO replace(Integer id, SolicitacaoMudancaRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "SolicitacaoMudanca não encontrada: " + id));

        SolicitacaoMudanca entity = mapper.toEntity(dto);

        Projeto projeto = projetoRepository.findById(dto.projetoId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Projeto não encontrado: " + dto.projetoId()));

        StatusSolicitacao status = statusSolicitacaoRepository.findById(dto.statusSolicitacaoId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "StatusSolicitacao não encontrado: " + dto.statusSolicitacaoId()));

        entity.setId(id);
        entity.setProjeto(projeto);
        entity.setStatusSolicitacao(status);

        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        SolicitacaoMudanca entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "SolicitacaoMudanca não encontrada: " + id));
        repository.delete(entity);
    }

}