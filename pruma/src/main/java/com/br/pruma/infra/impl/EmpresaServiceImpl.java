package com.br.pruma.infra.impl;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;
import com.br.pruma.application.mapper.EmpresaMapper;
import com.br.pruma.core.domain.Empresa;
import com.br.pruma.core.repository.EmpresaRepository;
import com.br.pruma.infra.repository.EmpresaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository repository;
    private final EmpresaMapper mapper;

    @Override
    public EmpresaResponseDTO salvar(EmpresaRequestDTO dto) {
        Empresa entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toResponseDTO(entity);
    }

    @Override
    public EmpresaResponseDTO buscarPorCnpj(String cnpj) {
        Empresa empresa = repository.findById(cnpj)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada"));
        return mapper.toResponseDTO(empresa);
    }

    @Override
    public List<EmpresaResponseDTO> listarTodas() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public void deletar(String cnpj) {
        if (!repository.existsById(cnpj)) {
            throw new EntityNotFoundException("Empresa não encontrada");
        }
        repository.deleteById(cnpj);
    }
}
