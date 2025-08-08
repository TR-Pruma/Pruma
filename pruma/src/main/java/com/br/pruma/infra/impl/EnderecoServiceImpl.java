package com.br.pruma.infra.impl;

import com.br.pruma.application.dto.request.EnderecoRequestDTO;
import com.br.pruma.application.dto.response.EnderecoResponseDTO;
import com.br.pruma.application.mapper.EnderecoMapper;
import com.br.pruma.core.domain.Endereco;
import com.br.pruma.core.repository.EnderecoRepository;
import com.br.pruma.infra.repository.EnderecoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository repository;
    private final EnderecoMapper mapper;

    @Override
    public EnderecoResponseDTO salvar(EnderecoRequestDTO dto) {
        Endereco entity = mapper.toEntity(dto);
        repository.save(entity);
        return mapper.toResponseDTO(entity);
    }

    @Override
    public EnderecoResponseDTO buscarPorId(Integer id) {
        Endereco endereco = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
        return mapper.toResponseDTO(endereco);
    }

    @Override
    public List<EnderecoResponseDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public EnderecoResponseDTO atualizar(Integer id, EnderecoRequestDTO dto) {
        Endereco endereco = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));
        Endereco atualizado = mapper.toEntity(dto);
        atualizado.setId(endereco.getId()); // preserva o ID
        repository.save(atualizado);
        return mapper.toResponseDTO(atualizado);
    }

    @Override
    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Endereço não encontrado");
        }
        repository.deleteById(id);
    }
}