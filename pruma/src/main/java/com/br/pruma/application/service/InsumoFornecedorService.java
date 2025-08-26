package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.InsumoFornecedorRequestDTO;
import com.br.pruma.application.dto.response.InsumoFornecedorResponseDTO;
import com.br.pruma.application.mapper.InsumoFornecedorMapper;
import com.br.pruma.config.ResourceNotFoundException;
import com.br.pruma.core.domain.InsumoFornecedor;
import com.br.pruma.core.domain.InsumoFornecedorAux;
import com.br.pruma.core.repository.InsumoFornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsumoFornecedorService {

    private final InsumoFornecedorRepository repository;
    private final InsumoFornecedorMapper mapper;

    @Transactional
    public InsumoFornecedorResponseDTO create(InsumoFornecedorRequestDTO request) {
        InsumoFornecedor entity = mapper.toEntity(request);
        InsumoFornecedor saved  = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<InsumoFornecedorResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public InsumoFornecedorResponseDTO findById(Integer insumoId,
                                                Integer fornecedorId) {
        InsumoFornecedorAux key = new InsumoFornecedorAux(insumoId, fornecedorId);
        InsumoFornecedor entity = repository.findById(key)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "InsumoFornecedor não encontrado para insumoId="
                                        + insumoId + ", fornecedorId=" + fornecedorId
                        )
                );
        return mapper.toResponse(entity);
    }
    @Transactional
    public InsumoFornecedorResponseDTO update(Integer insumoId,
                                              Integer fornecedorId,
                                              InsumoFornecedorRequestDTO request) {
        InsumoFornecedorAux key = new InsumoFornecedorAux(insumoId, fornecedorId);
        InsumoFornecedor entity = repository.findById(key)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "InsumoFornecedor não encontrado para atualização: insumoId="
                                        + insumoId + ", fornecedorId=" + fornecedorId
                        )
                );
        mapper.updateFromRequest(request, entity);
        InsumoFornecedor updated = repository.save(entity);
        return mapper.toResponse(updated);
    }
    @Transactional
    public void delete(Integer insumoId,
                       Integer fornecedorId) {
        InsumoFornecedorAux key = new InsumoFornecedorAux(insumoId, fornecedorId);
        if (!repository.existsById(key)) {
            throw new ResourceNotFoundException(
                    "InsumoFornecedor não encontrado para remoção: insumoId="
                            + insumoId + ", fornecedorId=" + fornecedorId
            );
        }
        repository.deleteById(key);
    }

    @Override
    public List<InsumoFornecedorResponseDTO> findAllByInsumo(Integer insumoId) {
        return repository.findByIdInsumoId(insumoId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private InsumoFornecedorResponseDTO toResponseDTO(InsumoFornecedor entity) {
        return InsumoFornecedorResponseDTO.builder()
                .insumoId(entity.getId().getInsumo().id())
                .fornecedorId(entity.getId().getFornecedor().getId())
                .preco(entity.getPreco())
                .build();
    }

}
