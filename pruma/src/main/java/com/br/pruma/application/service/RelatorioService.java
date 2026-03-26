package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.RelatorioRequestDTO;
import com.br.pruma.application.dto.response.RelatorioResponseDTO;
import com.br.pruma.application.mapper.RelatorioMapper;
import com.br.pruma.core.domain.Relatorio;
import com.br.pruma.core.repository.RelatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final RelatorioRepository repository;
    private final RelatorioMapper mapper;

    @Transactional(readOnly = true)
    public List<RelatorioResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RelatorioResponseDTO> listByObraId(Integer obraId) {
        return repository.findByObraId(obraId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public RelatorioResponseDTO getById(Integer id) {
        Relatorio entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relatório não encontrado com id " + id));
        return mapper.toDTO(entity);
    }

    @Transactional
    public RelatorioResponseDTO create(RelatorioRequestDTO request) {
        Relatorio entity = mapper.toEntity(request);
        Relatorio saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Transactional
    public RelatorioResponseDTO update(Integer id, RelatorioRequestDTO request) {
        Relatorio existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Relatório não encontrado com id " + id));
        mapper.updateFromDto(request, existing);
        Relatorio saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Relatório não encontrado com id " + id);
        }
        repository.deleteById(id);
    }
}
