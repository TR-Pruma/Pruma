package com.br.pruma.infra.impl;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.application.mapper.ClienteMapper;
import com.br.pruma.core.repository.ClienteService;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Endereco;
import com.br.pruma.core.repository.ClienteRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ClienteMapper clienteMapper;

    @Override
    @Transactional
    public ClienteResponseDTO criar(ClienteRequestDTO request) {
        validarClienteUnico(request);

        Endereco endereco = enderecoRepository.findById(request.getEnderecoId())
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));

        Cliente cliente = clienteMapper.toEntity(request, endereco);
        cliente = clienteRepository.save(cliente);

        return clienteMapper.toDTO(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClienteResponseDTO> listarTodos(Pageable pageable) {
        return clienteRepository.findByAtivoTrue(pageable)
                .map(clienteMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(Integer id) {
        Cliente cliente = buscarClientePorId(id);
        return clienteMapper.toDTO(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO atualizar(Integer id, ClienteRequestDTO request) {
        Cliente cliente = buscarClientePorId(id);
        validarClienteUnicoAtualizacao(request, id);

        Endereco endereco = enderecoRepository.findById(request.getEnderecoId())
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado"));

        clienteMapper.updateEntity(cliente, request, endereco);
        cliente = clienteRepository.save(cliente);

        return clienteMapper.toDTO(cliente);
    }

    @Override
    @Transactional
    public void deletar(Integer id) {
        Cliente cliente = buscarClientePorId(id);
        cliente.setAtivo(false);
        clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorCpf(String cpf) {
        return clienteRepository.findByCpfAndAtivoTrue(cpf)
                .map(clienteMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorEmail(String email) {
        return clienteRepository.findByEmailAndAtivoTrue(email)
                .map(clienteMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }

    @Override
    public boolean existePorCpf(String cpf) {
        return clienteRepository.existsByCpfAndAtivoTrue(cpf);
    }

    @Override
    public boolean existePorEmail(String email) {
        return clienteRepository.existsByEmailAndAtivoTrue(email);
    }

    private Cliente buscarClientePorId(Integer id) {
        return clienteRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }

    private void validarClienteUnico(ClienteRequestDTO request) {
        if (existePorCpf(request.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        if (existePorEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
    }

    private void validarClienteUnicoAtualizacao(ClienteRequestDTO request, Integer id) {
        clienteRepository.findByCpfAndAtivoTrue(request.getCpf())
                .ifPresent(cliente -> {
                    if (!cliente.getId().equals(id)) {
                        throw new IllegalArgumentException("CPF já cadastrado para outro cliente");
                    }
                });

        clienteRepository.findByEmailAndAtivoTrue(request.getEmail())
                .ifPresent(cliente -> {
                    if (!cliente.getId().equals(id)) {
                        throw new IllegalArgumentException("Email já cadastrado para outro cliente");
                    }
                });
    }
}
