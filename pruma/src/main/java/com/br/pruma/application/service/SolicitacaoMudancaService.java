package com.br.pruma.application.service;


import com.br.pruma.application.dto.request.SolicitacaoMudancaRequestDTO;
import com.br.pruma.application.dto.response.SolicitacaoMudancaResponseDTO;
import com.br.pruma.application.mapper.SolicitacaoMudancaMapper;
import com.br.pruma.core.domain.SolicitacaoMudanca;
import com.br.pruma.core.repository.SolicitacaoMudancaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitacaoMudancaService {

    private final SolicitacaoMudancaRepository repository;
    private final SolicitacaoMudancaMapper mapper;

    @Transactional(readOnly = true)
    public List<SolicitacaoMudancaResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoMudancaResponseDTO> listByProjetoId(Integer projetoId) {
        return repository.findByProjetoId(projetoId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoMudancaResponseDTO> listByStatusSolicitacaoId(Integer statusSolicitacaoId) {
        return repository.findByStatusSolicitacaoId(statusSolicitacaoId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public SolicitacaoMudancaResponseDTO getById(Integer id) {
        SolicitacaoMudanca entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação de mudança não encontrada com id " + id));
        return mapper.toDTO(entity);
    }

    @Transactional
    public SolicitacaoMudancaResponseDTO create(SolicitacaoMudancaRequestDTO request) {
        SolicitacaoMudanca entity = mapper.toEntity(request);
        SolicitacaoMudanca saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Transactional
    public SolicitacaoMudancaResponseDTO update(Integer id, SolicitacaoMudancaRequestDTO request) {
        SolicitacaoMudanca existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação de mudança não encontrada com id " + id));
        mapper.updateFromDto(request, existing);
        SolicitacaoMudanca saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitação de mudança não encontrada com id " + id);
        }
        repository.deleteById(id);
    }
}
