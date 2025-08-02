package com.br.pruma.application.service;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.application.mapper.ClienteMapper;
import com.br.pruma.config.RecursoNaoEncontradoException;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.EnderecoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ClienteMapper clienteMapper;

    @Transactional(readOnly = true)
    public ClienteResponseDTO findByCpf(String cpf) {
        return clienteRepository.findByCpfWithEndereco(cpf)
                .map(clienteMapper::toResponseDTO)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findByNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome).stream()
                .map(clienteMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public ClienteResponseDTO create(@Valid ClienteRequestDTO dto) {
        validarEmail(dto.getEmail(), dto.getCpf());
        if (clienteRepository.existsById(dto.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        validarEndereco(dto.getEnderecoId());

        Cliente cliente = clienteMapper.toEntity(dto);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(cliente);
    }

    @Transactional
    public ClienteResponseDTO update(String cpf, @Valid ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findByCpfWithEndereco(cpf)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado"));

        if (!cpf.equals(dto.getCpf())) {
            throw new IllegalArgumentException("Não é permitido alterar o CPF");
        }

        validarEmail(dto.getEmail(), cpf);
        validarEndereco(dto.getEnderecoId());

        clienteMapper.updateEntity(cliente, dto);
        cliente = clienteRepository.save(cliente);
        return clienteMapper.toResponseDTO(cliente);
    }

    @Transactional
    public void delete(String cpf) {
        if (!clienteRepository.existsById(cpf)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado");
        }
        clienteRepository.softDelete(cpf);
    }

    private void validarEmail(String email, String cpf) {
        if (clienteRepository.existsByEmailAndAtivoTrueAndCpfNot(email, cpf)) {
            throw new IllegalArgumentException("Email já cadastrado para outro cliente");
        }
    }

    private void validarEndereco(Integer enderecoId) {
        if (enderecoId != null && !enderecoRepository.existsById(enderecoId)) {
            throw new RecursoNaoEncontradoException("Endereço não encontrado");
        }
    }
}
