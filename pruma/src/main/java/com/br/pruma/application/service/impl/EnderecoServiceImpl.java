package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.EnderecoRequestDTO;
import com.br.pruma.application.dto.response.EnderecoResponseDTO;
import com.br.pruma.application.mapper.EnderecoMapper;
import com.br.pruma.application.service.EnderecoService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Endereco;
import com.br.pruma.core.repository.EnderecoRepository;
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
        Endereco endereco = mapper.toEntity(dto);
        return mapper.toResponseDTO(repository.save(endereco));
    }

    @Override
    public EnderecoResponseDTO buscarPorId(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço com ID " + id + " não encontrado."));
    }

    @Override
    public List<EnderecoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public EnderecoResponseDTO atualizar(Integer id, EnderecoRequestDTO dto) {
        Endereco existente = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço com ID " + id + " não encontrado."));
        Endereco atualizado = mapper.toEntity(dto);
        atualizado.setId(existente.getId());
        return mapper.toResponseDTO(repository.save(atualizado));
    }

    @Override
    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Não é possível deletar. Endereço com ID " + id + " não existe.");
        }
        repository.deleteById(id);
    }
}
