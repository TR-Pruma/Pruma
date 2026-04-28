package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.FornecedorRequestDTO;
import com.br.pruma.application.dto.response.FornecedorResponseDTO;
import com.br.pruma.application.dto.update.FornecedorUpdateDTO;
import com.br.pruma.application.mapper.FornecedorMapper;
import com.br.pruma.application.service.FornecedorService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Fornecedor;
import com.br.pruma.core.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FornecedorServiceImpl implements FornecedorService {

    private final FornecedorRepository repository;
    private final FornecedorMapper mapper;

    @Override
    public FornecedorResponseDTO create(FornecedorRequestDTO dto) {
        Fornecedor entity = mapper.toEntity(dto);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public FornecedorResponseDTO getById(Integer id) {
        return mapper.toResponse(repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Fornecedor com ID " + id + " não encontrado.")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FornecedorResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FornecedorResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public FornecedorResponseDTO update(Integer id, FornecedorUpdateDTO dto) {
        Fornecedor entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Fornecedor com ID " + id + " não encontrado."));
        mapper.updateFromDto(dto, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public FornecedorResponseDTO replace(Integer id, FornecedorRequestDTO dto) {
        repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Fornecedor com ID " + id + " não encontrado."));
        Fornecedor entity = mapper.toEntity(dto);
        entity.setId(id);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        Fornecedor entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Fornecedor com ID " + id + " não encontrado."));
        entity.setAtivo(false);
        repository.save(entity);
    }
}
