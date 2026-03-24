package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.InsumoFornecedorRequestDTO;
import com.br.pruma.application.dto.response.InsumoFornecedorResponseDTO;
import com.br.pruma.application.mapper.InsumoFornecedorMapper;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.InsumoFornecedor;
import com.br.pruma.core.domain.InsumoFornecedorAux;
import com.br.pruma.core.domain.InsumoFornecedorAuxId;
import com.br.pruma.core.repository.InsumoFornecedorAuxRepository;
import com.br.pruma.core.repository.InsumoFornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InsumoFornecedorService {

    private final InsumoFornecedorRepository repository;
    private final InsumoFornecedorMapper mapper;
    private final InsumoFornecedorAuxRepository repositoryInsumoFornecedorAux;

    @Transactional
    public InsumoFornecedorResponseDTO create(InsumoFornecedorRequestDTO request) {
        InsumoFornecedor entity = mapper.toEntity(request);
        return mapper.toResponseDTO(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<InsumoFornecedorResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public InsumoFornecedorResponseDTO findById(Integer insumoId, Integer fornecedorId) {
        return mapper.toResponseDTO(findEntity(insumoId, fornecedorId));
    }

    @Transactional
    public InsumoFornecedorResponseDTO update(Integer insumoId,
                                              Integer fornecedorId,
                                              InsumoFornecedorRequestDTO request) {
        InsumoFornecedor entity = findEntity(insumoId, fornecedorId);
        entity.setPreco(request.getPreco());
        return mapper.toResponseDTO(repository.save(entity));
    }

    @Transactional
    public void delete(Integer insumoId, Integer fornecedorId) {
        InsumoFornecedorAuxId key = new InsumoFornecedorAuxId(insumoId, fornecedorId);
        if (!repository.existsById(key)) {
            throw new RecursoNaoEncontradoException(
                    "InsumoFornecedor não encontrado para remoção: insumoId=%d, fornecedorId=%d"
                            .formatted(insumoId, fornecedorId)
            );
        }
        repository.deleteById(key);
    }

    @Transactional(readOnly = true)
    public List<InsumoFornecedorResponseDTO> findAllByInsumo(Integer insumoId) {
        return repository.findByIdInsumoId(insumoId).stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    private InsumoFornecedor findEntity(Integer insumoId, Integer fornecedorId) {
        InsumoFornecedorAuxId key = new InsumoFornecedorAuxId(insumoId, fornecedorId);
        return repository.findById(key)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "InsumoFornecedor não encontrado: insumoId=%d, fornecedorId=%d"
                                .formatted(insumoId, fornecedorId)
                ));
    }

    public List<InsumoFornecedorAux> listarPorInsumo(Integer insumoId) {
        return repositoryInsumoFornecedorAux.findByIdInsumoId(insumoId);
    }

    public Optional<InsumoFornecedorAux> buscarPorChave(Integer insumoId, Integer fornecedorId) {
        return repositoryInsumoFornecedorAux.findByIdInsumoIdAndIdFornecedorId(insumoId, fornecedorId);
    }
}
