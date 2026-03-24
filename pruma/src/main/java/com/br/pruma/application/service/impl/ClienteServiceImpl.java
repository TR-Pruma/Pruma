package com.br.pruma.application.service.impl;

import com.br.pruma.application.dto.request.ClienteRequestDTO;
import com.br.pruma.application.dto.response.ClienteResponseDTO;
import com.br.pruma.application.service.ClienteService;
import com.br.pruma.core.domain.Cliente;
import com.br.pruma.core.domain.Endereco;
import com.br.pruma.core.repository.ClienteRepository;
import com.br.pruma.core.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteRequestDTO dto) {
        Endereco endereco = enderecoRepository.findById(dto.enderecoId())
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado: " + dto.enderecoId()));

        Cliente cliente = Cliente.builder()
                .cpf(dto.cpf())
                .nome(dto.nome())
                .email(dto.email())
                .telefone(dto.telefone())
                .endereco(endereco)
                .senha(dto.senha())
                .ativo(dto.ativo())
                .build();

        return toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(Integer id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + id));

        Endereco endereco = enderecoRepository.findById(dto.enderecoId())
                .orElseThrow(() -> new EntityNotFoundException("Endereço não encontrado: " + dto.enderecoId()));

        cliente.setCpf(dto.cpf());
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setTelefone(dto.telefone());
        cliente.setEndereco(endereco);
        cliente.setSenha(dto.senha());
        cliente.setAtivo(dto.ativo());

        return toResponse(clienteRepository.save(cliente));
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponseDTO findById(Integer id) {
        return clienteRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> findAll() {
        return clienteRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente não encontrado: " + id);
        }
        clienteRepository.deleteById(id);
    }

    private ClienteResponseDTO toResponse(Cliente c) {
        return new ClienteResponseDTO(
                c.getId(),
                c.getCpf(),
                c.getNome(),
                c.getEmail(),
                c.getTelefone(),
                c.getEndereco() != null ? c.getEndereco().getId() : null,
                c.getCreatedAt(),
                c.getUpdatedAt(),
                c.getVersion(),
                c.getAtivo()
        );
    }
}
