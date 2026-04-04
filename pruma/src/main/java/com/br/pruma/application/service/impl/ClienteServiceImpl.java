package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.application.mapper.ClienteMapper;
import com.br.pruma.application.service.ClienteService;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Endereco;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public ClienteResponseDTO create(ClienteRequestDTO dto) {
        Endereco endereco = enderecoRepository.findById(dto.enderecoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Endereço com ID " + dto.enderecoId() + " não encontrado."));
        Cliente cliente = clienteMapper.toEntity(dto, endereco);
        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponseDTO update(Integer id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Cliente com ID " + id + " não encontrado."));
        Endereco endereco = enderecoRepository.findById(dto.enderecoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Endereço com ID " + dto.enderecoId() + " não encontrado."));
        clienteMapper.updateEntity(cliente, dto, endereco);
        return clienteMapper.toDto(clienteRepository.save(cliente));
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO findById(Integer id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toDto)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Cliente com ID " + id + " não encontrado."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Integer id) {
        Cliente entity = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Cliente com ID " + id + " não encontrado."));
        clienteRepository.delete(entity);
    }
}
