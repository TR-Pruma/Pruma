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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository repository;
    private final EnderecoMapper mapper;

    @Override
    public EnderecoResponseDTO salvar(EnderecoRequestDTO dto) {
        Endereco endereco = mapper.toEntity(dto);
        return mapper.toResponseDTO(repository.save(endereco));
    }

    @Override
    @Transactional(readOnly = true)
    public EnderecoResponseDTO buscarPorId(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Endereço com ID " + id + " não encontrado."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnderecoResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public EnderecoResponseDTO atualizar(Integer id, EnderecoRequestDTO dto) {
        Endereco existente = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Endereço com ID " + id + " não encontrado."));
        mapper.updateFromDto(dto, existente);
        return mapper.toResponseDTO(repository.save(existente));
    }

    @Override
    public void deletar(Integer id) {
        Endereco entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Endereço com ID " + id + " não encontrado."));
        repository.delete(entity);
    }
}
