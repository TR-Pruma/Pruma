package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.FornecedorRequestDTO;
import com.br.pruma.application.dto.response.FornecedorResponseDTO;
import com.br.pruma.application.service.FornecedorService;
import com.br.pruma.core.domain.Fornecedor;
import com.br.pruma.core.repository.FornecedorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorServiceImpl implements FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    @Override
    @Transactional
    public FornecedorResponseDTO criar(FornecedorRequestDTO request) {
        Fornecedor entity = Fornecedor.builder()
                .cnpj(request.cnpj())
                .nome(request.nome())
                .email(request.email())
                .telefone(request.telefone())
                .build();
        return toResponse(fornecedorRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FornecedorResponseDTO> listarTodos() {
        return fornecedorRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FornecedorResponseDTO buscarPorId(Integer id) {
        return fornecedorRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado: " + id));
    }

    @Override
    @Transactional
    public FornecedorResponseDTO atualizar(Integer id, FornecedorRequestDTO request) {
        Fornecedor entity = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado: " + id));
        entity.setCnpj(request.cnpj());
        entity.setNome(request.nome());
        entity.setEmail(request.email());
        entity.setTelefone(request.telefone());
        return toResponse(fornecedorRepository.save(entity));
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Fornecedor não encontrado: " + id);
        }
        fornecedorRepository.deleteById(id);
    }

    private FornecedorResponseDTO toResponse(Fornecedor e) {
        return new FornecedorResponseDTO(
                e.getId(),
                e.getCnpj(),
                e.getNome(),
                e.getEmail(),
                e.getTelefone(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getVersion()
        );
    }
}
