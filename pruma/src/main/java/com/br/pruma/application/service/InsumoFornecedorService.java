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

@Service
@RequiredArgsConstructor
public class InsumoFornecedorService {

    private final InsumoFornecedorRepository repository;
    private final InsumoFornecedorMapper mapper;

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
        InsumoFornecedorAux key = new InsumoFornecedorAux(insumoId, fornecedorId);
        if (!repository.existsById(key)) {
            throw new ResourceNotFoundException(
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

    // ====== Utilitário interno ======
    private InsumoFornecedor findEntity(Integer insumoId, Integer fornecedorId) {
        InsumoFornecedorAux key = new InsumoFornecedorAux(insumoId, fornecedorId);
        return repository.findById(key)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "InsumoFornecedor não encontrado: insumoId=%d, fornecedorId=%d"
                                .formatted(insumoId, fornecedorId)
                ));
    }
}