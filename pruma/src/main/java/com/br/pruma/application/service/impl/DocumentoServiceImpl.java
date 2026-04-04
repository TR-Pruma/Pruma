package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.DocumentoRequestDTO;
import com.br.pruma.application.dto.response.DocumentoResponseDTO;
import com.br.pruma.application.dto.update.DocumentoUpdateDTO;
import com.br.pruma.application.mapper.DocumentoMapper;
import com.br.pruma.application.service.DocumentoService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Documento;
import com.br.pruma.core.repository.DocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository repository;
    private final DocumentoMapper mapper;

    @Override
    public DocumentoResponseDTO create(DocumentoRequestDTO dto) {
        Documento entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentoResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Documento com ID " + id + " não encontrado.")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DocumentoResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public DocumentoResponseDTO update(Integer id, DocumentoUpdateDTO dto) {
        Documento entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Documento com ID " + id + " não encontrado."));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        Documento entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Documento com ID " + id + " não encontrado."));
        repository.delete(entity);
    }
}
