package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;
import com.br.pruma.application.mapper.EmpresaMapper;
import com.br.pruma.core.domain.Empresa;
import com.br.pruma.core.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository repository;
    private final EmpresaMapper mapper;

    /**
     * Cria ou atualiza uma empresa.
     */
    @Transactional
    public EmpresaResponseDTO salvar(EmpresaRequestDTO dto) {
        Empresa entity = mapper.toEntity(dto);
        Empresa saved = repository.save(entity);
        return mapper.toResponseDto(saved);
    }

    /**
     * Busca empresa pelo CNPJ.
     */
    @Transactional(readOnly = true)
    public EmpresaResponseDTO buscarPorCnpj(String cnpj) {
        Empresa entity = repository.findByCnpj(cnpj)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com CNPJ " + cnpj));
        return mapper.toResponseDto(entity);
    }

    /**
     * Lista todas as empresas.
     */
    @Transactional(readOnly = true)
    public List<EmpresaResponseDTO> listarTodas() {
        return repository.findAll().stream()
                .map(mapper::toResponseDto)
                .toList();
    }

    /**
     * Deleta empresa pelo CNPJ.
     */
    @Transactional
    public void deletar(String cnpj) {
        Empresa entity = repository.findByCnpj(cnpj)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada com CNPJ " + cnpj));
        repository.delete(entity);
    }
}
