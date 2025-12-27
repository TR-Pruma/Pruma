package com.br.pruma.application.service;


import com.br.pruma.application.dto.request.RequisicaoMaterialRequestDTO;
import com.br.pruma.application.dto.response.RequisicaoMaterialResponseDTO;
import com.br.pruma.application.mapper.RequisicaoMaterialMapper;
import com.br.pruma.core.domain.RequisicaoMaterial;
import com.br.pruma.core.repository.RequisicaoMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequisicaoMaterialService {

    private final RequisicaoMaterialRepository repository;
    private final RequisicaoMaterialMapper mapper;

    @Transactional(readOnly = true)
    public List<RequisicaoMaterialResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RequisicaoMaterialResponseDTO> listByObraId(Integer obraId) {
        return repository.findByObraId(obraId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RequisicaoMaterialResponseDTO> listByMaterialId(Integer materialId) {
        return repository.findByMaterialId(materialId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public RequisicaoMaterialResponseDTO getById(Integer id) {
        RequisicaoMaterial entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Requisição de material não encontrada com id " + id));
        return mapper.toDTO(entity);
    }

    @Transactional
    public RequisicaoMaterialResponseDTO create(RequisicaoMaterialRequestDTO request) {
        RequisicaoMaterial entity = mapper.toEntity(request);
        RequisicaoMaterial saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Transactional
    public RequisicaoMaterialResponseDTO update(Integer id, RequisicaoMaterialRequestDTO request) {
        RequisicaoMaterial existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Requisição de material não encontrada com id " + id));
        mapper.updateFromDto(request, existing);
        RequisicaoMaterial saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Requisição de material não encontrada com id " + id);
        }
        repository.deleteById(id);
    }
}
