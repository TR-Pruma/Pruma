package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.EmpresaRequestDTO;
import com.br.pruma.application.dto.response.EmpresaResponseDTO;
import com.br.pruma.application.service.EmpresaService;
import com.br.pruma.core.domain.Empresa;
import com.br.pruma.core.repository.EmpresaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Override
    @Transactional
    public EmpresaResponseDTO criar(EmpresaRequestDTO request) {
        Empresa entity = Empresa.builder()
                .cnpj(request.cnpj())
                .nome(request.nome())
                .build();
        return toResponse(empresaRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmpresaResponseDTO> listarTodos() {
        return empresaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EmpresaResponseDTO buscarPorId(Integer id) {
        return empresaRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada: " + id));
    }

    @Override
    @Transactional
    public EmpresaResponseDTO atualizar(Integer id, EmpresaRequestDTO request) {
        Empresa entity = empresaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada: " + id));
        entity.setCnpj(request.cnpj());
        entity.setNome(request.nome());
        return toResponse(empresaRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!empresaRepository.existsById(id)) {
            throw new EntityNotFoundException("Empresa não encontrada: " + id);
        }
        empresaRepository.deleteById(id);
    }

    private EmpresaResponseDTO toResponse(Empresa e) {
        return new EmpresaResponseDTO(
                e.getId(),
                e.getCnpj(),
                e.getNome(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
