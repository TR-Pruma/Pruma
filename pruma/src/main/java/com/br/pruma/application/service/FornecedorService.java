package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.FornecedorRequestDTO;
import com.br.pruma.application.dto.response.FornecedorResponseDTO;
import com.br.pruma.application.mapper.FornecedorMapper;
import com.br.pruma.core.domain.Fornecedor;
import com.br.pruma.core.repository.FornecedorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorService {
    private final FornecedorRepository repository;
    private final FornecedorMapper mapper;

    public FornecedorResponseDTO salvar(FornecedorRequestDTO requestDTO) {
        if (repository.existsByCnpj(requestDTO.getCnpj())) {
            throw new IllegalArgumentException("CNPJ já cadastrado.");
        }
        Fornecedor fornecedor = mapper.toEntity(requestDTO);
        return mapper.toResponse(repository.save(fornecedor));
    }

    public List<FornecedorResponseDTO> listarTodos() {
        return mapper.toResponseList(repository.findAll());
    }

    public FornecedorResponseDTO buscarPorId(Integer id) {
        Fornecedor fornecedor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));
        return mapper.toResponse(fornecedor);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Fornecedor não encontrado");
        }
        repository.deleteById(id);
    }
    public FornecedorResponseDTO atualizar(Integer id, FornecedorRequestDTO requestDTO) {
        Fornecedor fornecedor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado"));


        if (!fornecedor.getCnpj().equals(requestDTO.getCnpj()) &&
                repository.existsByCnpj(requestDTO.getCnpj())) {
            throw new IllegalArgumentException("CNPJ já cadastrado.");
        }

        fornecedor.setNome(requestDTO.getNome());
        fornecedor.setCnpj(requestDTO.getCnpj());
        fornecedor.setContato(requestDTO.getContato());

        return mapper.toResponse(repository.save(fornecedor));
    }

}
