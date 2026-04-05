package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.InsumoFornecedorRequestDTO;
import com.br.pruma.application.dto.response.InsumoFornecedorResponseDTO;
import com.br.pruma.application.dto.update.InsumoFornecedorUpdateDTO;
import com.br.pruma.application.mapper.InsumoFornecedorMapper;
import com.br.pruma.application.service.InsumoFornecedorService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.InsumoFornecedor;
import com.br.pruma.core.domain.InsumoFornecedorAuxId;
import com.br.pruma.core.repository.InsumoFornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InsumoFornecedorServiceImpl implements InsumoFornecedorService {

    private final InsumoFornecedorRepository repository;
    private final InsumoFornecedorMapper mapper;

    @Override
    public InsumoFornecedorResponseDTO create(InsumoFornecedorRequestDTO dto) {
        InsumoFornecedor entity = mapper.toEntity(dto);
        return mapper.toResponseDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public InsumoFornecedorResponseDTO getById(Integer id) {
        throw new UnsupportedOperationException(
                "InsumoFornecedor usa chave composta. Use getByIds(insumoId, fornecedorId).");
    }

    @Override
    @Transactional(readOnly = true)
    public List<InsumoFornecedorResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InsumoFornecedorResponseDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InsumoFornecedorResponseDTO> listByInsumo(Integer insumoId) {
        return repository.findByIdInsumoId(insumoId).stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InsumoFornecedorResponseDTO> listByFornecedor(Integer fornecedorId) {
        return repository.findAll().stream()
                .filter(e -> e.getId() != null && fornecedorId.equals(e.getId().getFornecedorId()))
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public InsumoFornecedorResponseDTO update(Integer id, InsumoFornecedorUpdateDTO dto) {
        throw new UnsupportedOperationException(
                "InsumoFornecedor usa chave composta. Use updateByIds(insumoId, fornecedorId, dto).");
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException(
                "InsumoFornecedor usa chave composta. Use deleteByIds(insumoId, fornecedorId).");
    }
}
